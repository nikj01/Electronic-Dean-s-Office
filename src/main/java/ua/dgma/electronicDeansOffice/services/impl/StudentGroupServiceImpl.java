package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.*;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.RegisterStudentGroupData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.UpdateStudentGroupData;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;
import ua.dgma.electronicDeansOffice.services.specifications.StudentGroupSpecifications;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByNameData;
import ua.dgma.electronicDeansOffice.utill.validators.AbstractValidator;
import ua.dgma.electronicDeansOffice.utill.validators.data.DataForAbstractValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.*;

@Service
@Transactional(readOnly = true)
public class StudentGroupServiceImpl implements StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final PeopleService<Student> studentService;
    private final DepartmentRepository departmentRepository;
    private final AbstractValidator studentGroupValidator;
    private final ExceptionData exceptionData;
    private final StudentGroupSpecifications specifications;
    private String className;

    @Autowired
    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository,
                                   TeacherRepository teacherRepository,
                                   StudentRepository studentRepository,
                                   PeopleService<Student> studentService,
                                   DepartmentRepository departmentRepository,
                                   AbstractValidator studentGroupValidator,
                                   ExceptionData exceptionData,
                                   StudentGroupSpecifications specifications) {
        this.studentGroupRepository = studentGroupRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.departmentRepository = departmentRepository;
        this.studentGroupValidator = studentGroupValidator;
        this.exceptionData = exceptionData;
        this.specifications = specifications;
        this.className = StudentGroup.class.getSimpleName();
    }

    @Override
    public List<StudentGroup> findByName(String name) {
        return studentGroupRepository.getByNameContainingIgnoreCase(name).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "name", name)));
    }

    @Override
    public List<StudentGroup> findAllStudentGroups(FindAllData data) {
        if(data.getFacultyName() == null)
            return findAllWithPaginationOrWithout(data);
        else
            return findAllWithPaginationOrWithoutByFaculty(data);
    }
    private List<StudentGroup> findAllWithPaginationOrWithout(FindAllData data) {
        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return studentGroupRepository.findAll(specifications.getObjectByDeletedCriteria(data.getDeleted()));
        else
            return studentGroupRepository.findAll(specifications.getObjectByDeletedCriteria(data.getDeleted()), PageRequest.of(data.getPage(), data.getObjectsPerPage())).getContent();
    }
    private List<StudentGroup> findAllWithPaginationOrWithoutByFaculty(FindAllData data) {
        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return studentGroupRepository.findAll(Specification.where(specifications.getStudentGroupByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))));
        else
            return studentGroupRepository.findAll(Specification.where(specifications.getStudentGroupByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))), PageRequest.of(data.getPage(), data.getObjectsPerPage())).getContent();
    }

    @Override
    @Transactional
    public void registerNew(RegisterStudentGroupData data) {
        checkExistenceByNameBeforeRegistration(new CheckExistsByNameData<>(className, getStudentGroupName(data), studentGroupRepository));
        validateObject(new DataForAbstractValidator<>(studentGroupValidator, data.getNewStudentGroup()));

        StudentGroup newStudentGroup = data.getNewStudentGroup();

        setDepartmentInStudentGroup(newStudentGroup);

        saveStudentGroup(newStudentGroup);
        saveNewStudents(getNewStudentsFromNewStudentGroup(newStudentGroup), data);
    }
    private String getStudentGroupName(RegisterStudentGroupData data) {
        return data.getNewStudentGroup().getName();
    }
    private void setDepartmentInStudentGroup(StudentGroup studentGroup) {
        studentGroup.setDepartment(getDepartmentByName(getDepartmentName(studentGroup)));
    }
    private Department getDepartmentByName(String departmentName) {
        return departmentRepository.getByName(departmentName).get();
    }
    private String getDepartmentName(StudentGroup studentGroup) {
        return studentGroup.getDepartment().getName();
    }
    private void saveStudentGroup(StudentGroup studentGroup) {
        studentGroupRepository.save(studentGroup);
    }
    private void saveNewStudents(List<Student> students, RegisterStudentGroupData data) {
        for (Student student: students)
            studentService.registerNew(new RegisterPersonData<>(student, data.getBindingResult()));
    }
    private List<Student> getNewStudentsFromNewStudentGroup(StudentGroup studentGroup) {
        return studentGroup.getStudents();
    }

    @Override
    @Transactional
    public void updateByName(UpdateStudentGroupData data) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, data.getName(), studentGroupRepository));
        validateObject(new DataForAbstractValidator<>(studentGroupValidator, data.getUpdatedStudentGroup()));

        StudentGroup updatedStudentGroup = data.getUpdatedStudentGroup();

        setIdInStudentGroup(updatedStudentGroup, data);
        setDepartmentInStudentGroup(updatedStudentGroup);
        setStudentsInStudentGroup(updatedStudentGroup, data);
        setCuratorInStudentGroup(updatedStudentGroup, data);
        setGroupLeaderInStudentGroup(updatedStudentGroup, data);

        saveStudentGroup(updatedStudentGroup);
    }
    private void setIdInStudentGroup(StudentGroup studentGroup, UpdateStudentGroupData data) {
        studentGroup.setId(getStudentGroup(getStudentGroupName(data)).getId());
    }
    private StudentGroup getStudentGroup(String groupName) {
        return studentGroupRepository.getByName(groupName).get();
    }
    private String getStudentGroupName(UpdateStudentGroupData data) {
        return data.getName();
    }
    private void setStudentsInStudentGroup(StudentGroup studentGroup, UpdateStudentGroupData data) {
        studentGroup.setStudents(getExistingStudents(data));
    }
    private List<Student> getExistingStudents(UpdateStudentGroupData data) {
        return getStudentGroup(getStudentGroupName(data)).getStudents();
    }
    private void setCuratorInStudentGroup(StudentGroup studentGroup, UpdateStudentGroupData data) {
        studentGroup.setCurator(teacherRepository.getByUid(getCuratorUid(studentGroup)).get());
    }
    private Long getCuratorUid(StudentGroup studentGroup) {
        return studentGroup.getCurator().getUid().longValue();
    }
    private void setGroupLeaderInStudentGroup(StudentGroup studentGroup, UpdateStudentGroupData data) {
        studentGroup.setGroupLeader(studentRepository.getByUid(getLeaderUid(studentGroup)).get());
    }
    private Long getLeaderUid(StudentGroup studentGroup) {
        return studentGroup.getGroupLeader().getUid().longValue();
    }


    @Override
    @Transactional
    public void deleteByName(String name) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, studentGroupRepository));

        studentGroupRepository.deleteByName(name);
    }

    @Override
    @Transactional
    public void softDeleteByName(String name) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, studentGroupRepository));

        markStudentsAsDeleted(getStudentGroup(name));

        saveStudentGroup(markStudentGroupAsDeleted(getStudentGroup(name)));
    }
    private void markStudentsAsDeleted(StudentGroup studentGroup) {
        studentGroup.getStudents().stream().forEach(student -> student.setDeleted(true));
    }
    private StudentGroup markStudentGroupAsDeleted(StudentGroup studentGroup) {
        studentGroup.setDeleted(true);
        return studentGroup;
    }

    @Override
    public void markStudentGroupsAsDeleted(List<StudentGroup> studentGroups) {
        for (StudentGroup group : studentGroups)
            markStudentGroupAsDeleted(group);
    }
}

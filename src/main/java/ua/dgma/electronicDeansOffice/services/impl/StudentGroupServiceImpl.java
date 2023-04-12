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
    private final StudentGroupSpecifications specifications;
    private String className;

    @Autowired
    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository,
                                   TeacherRepository teacherRepository,
                                   StudentRepository studentRepository,
                                   PeopleService<Student> studentService,
                                   DepartmentRepository departmentRepository,
                                   AbstractValidator studentGroupValidator,
                                   StudentGroupSpecifications specifications) {
        this.studentGroupRepository = studentGroupRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.departmentRepository = departmentRepository;
        this.studentGroupValidator = studentGroupValidator;
        this.specifications = specifications;
        this.className = StudentGroup.class.getSimpleName();
    }

    @Override
    public List<StudentGroup> findByName(String name) {
        return studentGroupRepository.getByNameContainingIgnoreCase(name).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "name", name)));
    }

    @Override
    public List<StudentGroup> findAll(FindAllData data) {
        if (checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return findAllWithSpec(getSpec(data));
        else
            return findAllWithSpecAndPagination(getSpec(data), data);
    }

    private List<StudentGroup> findAllWithSpec(Specification spec) {
        return departmentRepository.findAll(spec);
    }

    private List<StudentGroup> findAllWithSpecAndPagination(Specification spec, FindAllData data) {
        return departmentRepository.findAll(spec, PageRequest.of(data.getPage(), data.getObjectsPerPage())).getContent();
    }

    private Specification getSpec(FindAllData data) {
        return Specification.where(specifications.getStudentGroupByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted())));
    }

    @Override
    @Transactional
    public void registerNew(RegisterStudentGroupData data) {
        checkExistenceByNameBeforeRegistration(new CheckExistsByNameData<>(className, getStudentGroupName(data), studentGroupRepository));
        validateObject(new DataForAbstractValidator<>(studentGroupValidator, data.getNewStudentGroup()));

        StudentGroup newStudentGroup = data.getNewStudentGroup();

        setDepartmentInStudentGroup(newStudentGroup);

        saveStudentGroup(newStudentGroup);
        saveNewStudents(getNewStudents(newStudentGroup), data);
    }

    private String getStudentGroupName(RegisterStudentGroupData data) {
        return data.getNewStudentGroup().getName();
    }

    private void setDepartmentInStudentGroup(StudentGroup studentGroup) {
        studentGroup.setDepartment(getExistingDepartment(studentGroup));
    }

    private Department getExistingDepartment(StudentGroup studentGroup) {
        return departmentRepository.getByName(getDepartmentName(studentGroup)).get();
    }

    private String getDepartmentName(StudentGroup studentGroup) {
        return studentGroup.getDepartment().getName();
    }

    private void saveStudentGroup(StudentGroup studentGroup) {
        studentGroupRepository.save(studentGroup);
    }

    private void saveNewStudents(List<Student> students, RegisterStudentGroupData data) {
        for (Student student : students)
            studentService.registerNew(new RegisterPersonData<>(student, data.getBindingResult()));
    }

    private List<Student> getNewStudents(StudentGroup studentGroup) {
        return studentGroup.getStudents();
    }

    @Override
    @Transactional
    public void update(UpdateStudentGroupData data) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, data.getName(), studentGroupRepository));
        validateObject(new DataForAbstractValidator<>(studentGroupValidator, data.getUpdatedStudentGroup()));

        StudentGroup updatedStudentGroup = data.getUpdatedStudentGroup();

        setIdInStudentGroup(updatedStudentGroup, data);
        setDepartmentInStudentGroup(updatedStudentGroup);
        setStudentsInStudentGroup(updatedStudentGroup, data);
        setCuratorInStudentGroup(updatedStudentGroup);
        setGroupLeaderInStudentGroup(updatedStudentGroup);

        saveStudentGroup(updatedStudentGroup);
    }

    private void setIdInStudentGroup(StudentGroup studentGroup, UpdateStudentGroupData data) {
        studentGroup.setId(getExistingStudentGroup(data).getId());
    }

    private StudentGroup getExistingStudentGroup(UpdateStudentGroupData data) {
        return studentGroupRepository.getByName(getStudentGroupName(data)).get();
    }

    private String getStudentGroupName(UpdateStudentGroupData data) {
        return data.getName();
    }

    private void setStudentsInStudentGroup(StudentGroup studentGroup, UpdateStudentGroupData data) {
        studentGroup.setStudents(getExistingStudents(data));
    }

    private List<Student> getExistingStudents(UpdateStudentGroupData data) {
        return getExistingStudentGroup(data).getStudents();
    }

    private void setCuratorInStudentGroup(StudentGroup studentGroup) {
        studentGroup.setCurator(getTeacher(studentGroup));
    }

    private Teacher getTeacher(StudentGroup studentGroup) {
        return teacherRepository.getByUid(getCuratorUid(studentGroup)).get();
    }

    private Long getCuratorUid(StudentGroup studentGroup) {
        return studentGroup.getCurator().getUid().longValue();
    }

    private void setGroupLeaderInStudentGroup(StudentGroup studentGroup) {
        studentGroup.setGroupLeader(getLeader(studentGroup));
    }

    private Student getLeader(StudentGroup studentGroup) {
        return studentRepository.getByUid(getLeaderUid(studentGroup)).get();
    }

    private Long getLeaderUid(StudentGroup studentGroup) {
        return studentGroup.getGroupLeader().getUid().longValue();
    }

    @Override
    @Transactional
    public void delete(String name) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, studentGroupRepository));

        studentGroupRepository.deleteByName(name);
    }

    @Override
    @Transactional
    public void softDelete(String name) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, studentGroupRepository));

        StudentGroup studentGroup = getExistingStudentGroup(name);

        softDeleteStudents(studentGroup.getStudents());

        saveStudentGroup(markStudentGroupAsDeleted(getExistingStudentGroup(name)));
    }

    private StudentGroup getExistingStudentGroup(String groupName) {
        return studentGroupRepository.getByName(groupName).get();
    }

    private void softDeleteStudents(List<Student> students) {
        studentService.softDeletePeople(students);
    }

    private StudentGroup markStudentGroupAsDeleted(StudentGroup studentGroup) {
        studentGroup.setDeleted(true);
        return studentGroup;
    }

    @Override
    @Transactional
    public void softDeleteStudentGroups(List<StudentGroup> studentGroups) {
        for (StudentGroup group : studentGroups)
            softDelete(group.getName());
    }
}

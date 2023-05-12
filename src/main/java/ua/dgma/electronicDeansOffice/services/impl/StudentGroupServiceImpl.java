package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.RegisterStudentGroupData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.UpdateStudentGroupData;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;
import ua.dgma.electronicDeansOffice.services.specifications.StudentGroupSpecifications;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByNameData;
import ua.dgma.electronicDeansOffice.utill.validators.AbstractValidator;
import ua.dgma.electronicDeansOffice.utill.validators.data.DataForAbstractValidator;

import java.time.LocalDateTime;
import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.*;

@Service
@Transactional(readOnly = true)
public class StudentGroupServiceImpl implements StudentGroupService {
    private final StudentGroupRepository studentGroupRepository;
    private final DepartmentRepository departmentRepository;
    private final PeopleService<Teacher> teacherService;
    private final PeopleService<Student> studentService;
    private final AbstractValidator studentGroupValidator;
    private final StudentGroupSpecifications specifications;
    private String className;

    @Autowired
    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository,
                                   PeopleService<Teacher> teacherService,
                                   PeopleService<Student> studentService,
                                   DepartmentRepository departmentRepository,
                                   AbstractValidator studentGroupValidator,
                                   StudentGroupSpecifications specifications) {
        this.studentGroupRepository = studentGroupRepository;
        this.teacherService = teacherService;
        this.departmentRepository = departmentRepository;
        this.studentService = studentService;
        this.studentGroupValidator = studentGroupValidator;
        this.specifications = specifications;
        this.className = StudentGroup.class.getSimpleName();
    }

    @Override
    public StudentGroup findOne(Long groupId) {
        return studentGroupRepository.findById(groupId).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "id", groupId)));
    }
    @Override
    public StudentGroup findByStudent(Student student) {
        return studentGroupRepository.getByStudentsContaining(student).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "student_id", student.getUid())));
    }

    @Override
    public List<StudentGroup> findByName(String groupName) {
        return studentGroupRepository.getByNameContainingIgnoreCase(groupName).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "name", groupName)));
    }

    @Override
    public List<StudentGroup> findAll(FindAllData data) {
        if (checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return findAllWithSpec(getSpec(data));
        else
            return findAllWithSpecAndPagination(getSpec(data), data);
    }

    @Override
    public List<StudentGroup> findByFaculty(Long facultyId) {
        return studentGroupRepository.getByDepartment_Faculty_Id(facultyId).get();
    }

    private List<StudentGroup> findAllWithSpec(Specification spec) {
        return studentGroupRepository.findAll(spec, Sort.by("name"));
    }

    private List<StudentGroup> findAllWithSpecAndPagination(Specification spec, FindAllData data) {
        return studentGroupRepository.findAll(spec, PageRequest.of(data.getPage(), data.getObjectsPerPage(), Sort.by("name"))).getContent();
    }

    private Specification getSpec(FindAllData data) {
        return specifications.getSpecForStudentGroups(data);
    }

    @Override
    @Transactional
    public void register(RegisterStudentGroupData data) {
        checkExistenceObjectWithSuchNameBeforeRegistrationOrUpdate(new CheckExistsByNameData<>(className, getStudentGroupId(data), studentGroupRepository));
        validateObject(new DataForAbstractValidator<>(studentGroupValidator, getNewStudentGroup(data)));

        StudentGroup newStudentGroup = getNewStudentGroup(data);

        setDepartmentInStudentGroup(newStudentGroup);

        saveAll(newStudentGroup, data);
    }

    private String getStudentGroupId(RegisterStudentGroupData data) {
        return data.getNewStudentGroup().getName();
    }

    private StudentGroup getNewStudentGroup(RegisterStudentGroupData data) {
        return data.getNewStudentGroup();
    }

    private void setDepartmentInStudentGroup(StudentGroup studentGroup) {
        studentGroup.setDepartment(getExistingDepartment(studentGroup));
    }

    private Department getExistingDepartment(StudentGroup studentGroup) {
        return departmentRepository.findById(getDepartmentId(studentGroup)).get();
    }

    private Long getDepartmentId(StudentGroup studentGroup) {
        return studentGroup.getDepartment().getId();
    }

    private void saveAll(StudentGroup studentGroup, RegisterStudentGroupData data) {
        StudentGroup newStudentGroup = saveStudentGroup(studentGroup);

        if (getStudentsFromGroup(studentGroup) != null) {
            for (Student student : getStudentsFromGroup(studentGroup)) {
                setGroupForStudent(student, newStudentGroup);
                studentService.register(new RegisterPersonData<>(student, data.getBindingResult()));
            }
        }
    }

    private StudentGroup saveStudentGroup(StudentGroup studentGroup) {
        studentGroupRepository.save(studentGroup);
        return studentGroup;
    }

    private List<Student> getStudentsFromGroup(StudentGroup studentGroup) {
        return studentGroup.getStudents();
    }

    private void setGroupForStudent(Student student, StudentGroup studentGroup) {
        student.setStudentGroup(studentGroup);
    }

    @Override
    @Transactional
    public void update(UpdateStudentGroupData data) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData(className, getStudentGroupId(data), studentGroupRepository));
        validateObject(new DataForAbstractValidator<>(studentGroupValidator, getUpdatedStudentGroup(data)));

        StudentGroup updatedStudentGroup = getUpdatedStudentGroup(data);

        setDepartmentInStudentGroup(updatedStudentGroup);
        setStudentsInStudentGroup(updatedStudentGroup, data);
        setCuratorInStudentGroup(updatedStudentGroup);
        setGroupLeaderInStudentGroup(updatedStudentGroup);

        saveStudentGroup(updatedStudentGroup);
    }

    private Long getStudentGroupId(UpdateStudentGroupData data) {
        return data.getId();
    }

    private StudentGroup getStudentGroup(UpdateStudentGroupData data) {
        return data.getUpdatedStudentGroup();
    }

    private StudentGroup getUpdatedStudentGroup(UpdateStudentGroupData data) {
        StudentGroup updatedGroup = getStudentGroup(data);

        setIdInStudentGroup(updatedGroup, data);

        return updatedGroup;
    }

    private void setIdInStudentGroup(StudentGroup studentGroup, UpdateStudentGroupData data) {
        studentGroup.setId(getExistingStudentGroup(data).getId());
    }

    private StudentGroup getExistingStudentGroup(UpdateStudentGroupData data) {
        return studentGroupRepository.findById(getStudentGroupId(data)).get();
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
        return teacherService.findByUid(getCuratorUid(studentGroup));
    }

    private Long getCuratorUid(StudentGroup studentGroup) {
        return studentGroup.getCurator().getUid().longValue();
    }

    private void setGroupLeaderInStudentGroup(StudentGroup studentGroup) {
        studentGroup.setGroupLeader(getLeader(studentGroup));
    }

    private Student getLeader(StudentGroup studentGroup) {
        return studentService.findByUid(getLeaderUid(studentGroup));
    }

    private Long getLeaderUid(StudentGroup studentGroup) {
        return studentGroup.getGroupLeader().getUid().longValue();
    }

    @Override
    @Transactional
    public void delete(Long groupId) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData(className, groupId, studentGroupRepository));

        studentGroupRepository.deleteById(groupId);
    }

    @Override
    @Transactional
    public void softDelete(Long groupId) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData(className, groupId, studentGroupRepository));

        StudentGroup studentGroup = getExistingStudentGroup(groupId);

        softDeleteStudents(studentGroup.getStudents());

        saveStudentGroup(markStudentGroupAsDeleted(getExistingStudentGroup(groupId)));
    }

    private StudentGroup getExistingStudentGroup(Long groupId) {
        return studentGroupRepository.findById(groupId).get();
    }

    private void softDeleteStudents(List<Student> students) {
        studentService.softDeletePeople(students);
    }

    private StudentGroup markStudentGroupAsDeleted(StudentGroup studentGroup) {
        studentGroup.setDeleted(true);
        studentGroup.setWasDeleted(LocalDateTime.now());
        return studentGroup;
    }

    @Override
    @Transactional
    public void softDeleteStudentGroups(List<StudentGroup> studentGroups) {
        for (StudentGroup group : studentGroups)
            softDelete(group.getId());
    }
}

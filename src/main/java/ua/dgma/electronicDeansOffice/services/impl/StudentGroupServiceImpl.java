package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.ExceptionData;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;
import ua.dgma.electronicDeansOffice.services.specifications.Specifications;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.StudentGroupValidator;

import java.util.ArrayList;
import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkPaginationParameters;

@Service
@Transactional(readOnly = true)
public class StudentGroupServiceImpl implements StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final PeopleService<Student> studentService;
    private final DepartmentRepository departmentRepository;
    private final StudentGroupValidator studentGroupValidator;
    private final ExceptionData exceptionData;
    private final Specifications<StudentGroup> specifications;
    private String className;

    @Autowired
    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository,
                                   TeacherRepository teacherRepository,
                                   StudentRepository studentRepository,
                                   PeopleService<Student> studentService,
                                   DepartmentRepository departmentRepository,
                                   StudentGroupValidator studentGroupValidator,
                                   ExceptionData exceptionData,
                                   Specifications<StudentGroup> specifications) {
        this.studentGroupRepository = studentGroupRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.departmentRepository = departmentRepository;
        this.studentGroupValidator = studentGroupValidator;
        this.exceptionData = exceptionData;
        this.specifications = specifications;
        className = StudentGroup.class.getSimpleName();
    }

    @Override
    public StudentGroup findByName(String name) {
        return studentGroupRepository.getByName(name).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "name", name)));
    }

    @Override
    public List<StudentGroup> findAllWithPaginationOrWithout(Integer page, Integer studentGroupsPerPage, Boolean isDeleted) {
        if(checkPaginationParameters(page, studentGroupsPerPage))
            return studentGroupRepository.findAll(specifications.getObjectByDeletedCriteria(isDeleted));
        else
            return studentGroupRepository.findAll(specifications.getObjectByDeletedCriteria(isDeleted), PageRequest.of(page, studentGroupsPerPage)).getContent();
    }

    @Override
    public List<StudentGroup> findAllGroupsByCurator(Long curatorUid, Boolean isDeleted) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(Teacher.class.getSimpleName(), curatorUid, teacherRepository));

        return studentGroupRepository.findAll(Specification.where(specifications.getStudentGroupByCuratorCriteria(curatorUid)).and(specifications.getObjectByDeletedCriteria(isDeleted)));
    }

    @Override
    public List<StudentGroup> findAllGroupsByDepartment(String departmentName, Boolean isDeleted) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(Department.class.getSimpleName(), departmentName, departmentRepository));

        return studentGroupRepository.findAll(Specification.where(specifications.getStudentGroupByDepartmentCriteria(departmentName)).and(specifications.getObjectByDeletedCriteria(isDeleted)));
    }

    @Override
    @Transactional
    public void registerNew(StudentGroup studentGroup, BindingResult bindingResult) {
        validateObject(new ValidationData<>(studentGroupValidator, studentGroup, bindingResult));

        for (Student student: saveStudentGroupWithoutStudents(studentGroup))
            studentService.registerNew(student, bindingResult);
    }
    public List<Student> saveStudentGroupWithoutStudents(StudentGroup studentGroup) {
        List<Student> newStudents = studentGroup.getStudents();

        studentGroup.setStudents(new ArrayList<>());
        studentGroupRepository.save(studentGroup);

        return newStudents;
    }

    @Override
    @Transactional
    public void updateByName(String name, StudentGroup updatedStudentGroup, BindingResult bindingResult) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, name, studentGroupRepository));
        validateObject(new ValidationData<>(studentGroupValidator, updatedStudentGroup, bindingResult));

        updatedStudentGroup.setName(name);

        studentGroupRepository.save(updatedStudentGroup);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, name, studentGroupRepository));
        studentGroupRepository.deleteByName(name);
    }

    @Override
    @Transactional
    public void softDeleteByName(String name) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, name, studentGroupRepository));

        StudentGroup studentGroup = findByName(name);
        studentGroup.setDeleted(true);
        studentGroup.getStudents().stream().forEach(student -> student.setDeleted(true));

        studentGroupRepository.save(studentGroup);
    }
}

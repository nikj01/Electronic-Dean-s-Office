package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
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
import ua.dgma.electronicDeansOffice.services.specifications.StudentGroupSpecifications;
import ua.dgma.electronicDeansOffice.services.specifications.impl.SpecificationsImpl;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByNameData;
import ua.dgma.electronicDeansOffice.utill.validators.AbstractValidator;
import ua.dgma.electronicDeansOffice.utill.validators.data.DataForAbstractValidator;

import java.util.ArrayList;
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
        className = StudentGroup.class.getSimpleName();
    }

    @Override
    public StudentGroup findById(Long id) {
        return studentGroupRepository.findById(id).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "id", id)));
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
        checkExistsWithSuchName(new CheckExistsByNameData(Department.class.getSimpleName(), departmentName, departmentRepository));

        return studentGroupRepository.findAll(Specification.where(specifications.getStudentGroupByDepartmentCriteria(departmentName)).and(specifications.getObjectByDeletedCriteria(isDeleted)));
    }

    @Override
    @Transactional
    public void registerNew(StudentGroup studentGroup, BindingResult bindingResult) {
        checkExistenceByNameBeforeRegistration(new CheckExistsByNameData<>(StudentGroup.class.getSimpleName(), studentGroup.getName(), studentGroupRepository));
        validateObject(new DataForAbstractValidator<>(studentGroupValidator, studentGroup));

        studentGroup.setDepartment(departmentRepository.getByName(studentGroup.getDepartment().getName()).get());
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
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, studentGroupRepository));
        validateObject(new DataForAbstractValidator<>(studentGroupValidator, updatedStudentGroup));

        updatedStudentGroup.setId(studentGroupRepository.getByName(name).get().getId());
        updatedStudentGroup.setDepartment(departmentRepository.getByName(updatedStudentGroup.getDepartment().getName()).get());
        updatedStudentGroup.setCurator(teacherRepository.getByUid(updatedStudentGroup.getCurator().getUid().longValue()).get());
        updatedStudentGroup.setGroupLeader(studentRepository.getByUid(updatedStudentGroup.getGroupLeader().getUid().longValue()).get());
        updatedStudentGroup.setStudents(studentGroupRepository.getByName(name).get().getStudents());

        studentGroupRepository.save(updatedStudentGroup);
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

        StudentGroup studentGroup = findByName(name);
        studentGroup.getStudents().stream().forEach(student -> student.setDeleted(true));
        studentGroup.setDeleted(true);

        studentGroupRepository.save(studentGroup);
    }
}

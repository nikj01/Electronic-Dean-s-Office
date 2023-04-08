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
    public List<StudentGroup> findAllStudentGroups(FindAllData data) {
        if(data.getFacultyName() == null)
            return findAllWithPaginationOrWithout(data);
        else
            return findAllWithPaginationOrWithoutByFaculty(data);
    }

    public List<StudentGroup> findAllWithPaginationOrWithout(FindAllData data) {
        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return studentGroupRepository.findAll(specifications.getObjectByDeletedCriteria(data.getDeleted()));
        else
            return studentGroupRepository.findAll(specifications.getObjectByDeletedCriteria(data.getDeleted()), PageRequest.of(data.getPage(), data.getObjectsPerPage())).getContent();
    }

    public List<StudentGroup> findAllWithPaginationOrWithoutByFaculty(FindAllData data) {
        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return studentGroupRepository.findAll(Specification.where(specifications.getStudentGroupByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))));
        else
            return studentGroupRepository.findAll(Specification.where(specifications.getStudentGroupByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))), PageRequest.of(data.getPage(), data.getObjectsPerPage())).getContent();
    }

    @Override
    @Transactional
    public void registerNew(RegisterStudentGroupData data) {
        checkExistenceByNameBeforeRegistration(new CheckExistsByNameData<>(className, data.getNewStudentGroup().getName(), studentGroupRepository));
        validateObject(new DataForAbstractValidator<>(studentGroupValidator, data.getNewStudentGroup()));

        StudentGroup newStudentGroup = data.getNewStudentGroup();
        newStudentGroup.setDepartment(departmentRepository.getByName(newStudentGroup.getDepartment().getName()).get());

        for (Student student: saveStudentGroupWithoutStudents(newStudentGroup))
            studentService.registerNew(new RegisterPersonData<>(student, data.getBindingResult()));
    }

    public List<Student> saveStudentGroupWithoutStudents(StudentGroup studentGroup) {
        List<Student> newStudents = studentGroup.getStudents();

        studentGroup.setStudents(new ArrayList<>());
        studentGroupRepository.save(studentGroup);

        return newStudents;
    }

    @Override
    @Transactional
    public void updateByName(UpdateStudentGroupData data) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, data.getName(), studentGroupRepository));
        validateObject(new DataForAbstractValidator<>(studentGroupValidator, data.getUpdatedStudentGroup()));

        StudentGroup updatedStudentGroup = data.getUpdatedStudentGroup();
        updatedStudentGroup.setId(studentGroupRepository.getByName(data.getName()).get().getId());
        updatedStudentGroup.setDepartment(departmentRepository.getByName(updatedStudentGroup.getDepartment().getName()).get());
        updatedStudentGroup.setCurator(teacherRepository.getByUid(updatedStudentGroup.getCurator().getUid().longValue()).get());
        updatedStudentGroup.setGroupLeader(studentRepository.getByUid(updatedStudentGroup.getGroupLeader().getUid().longValue()).get());
        updatedStudentGroup.setStudents(studentGroupRepository.getByName(data.getName()).get().getStudents());

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

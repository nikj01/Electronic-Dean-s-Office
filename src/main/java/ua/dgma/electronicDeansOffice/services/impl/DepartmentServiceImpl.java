package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.department.RegisterDepartmentData;
import ua.dgma.electronicDeansOffice.services.impl.data.department.UpdateDepartmentData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.interfaces.DepartmentService;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.specifications.DepartmentSpecifications;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByNameData;
import ua.dgma.electronicDeansOffice.utill.validators.AbstractValidator;
import ua.dgma.electronicDeansOffice.utill.validators.data.DataForAbstractValidator;

import java.util.ArrayList;
import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.*;

@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final PeopleService<Teacher> teacherService;
    private final TeacherRepository teacherRepository;
    private final AbstractValidator departmentValidator;
    private final ExceptionData exceptionData;
    private final DepartmentSpecifications specifications;
    private String className;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 FacultyRepository facultyRepository,
                                 PeopleService<Teacher> teacherService,
                                 TeacherRepository teacherRepository,
                                 AbstractValidator departmentValidator,
                                 ExceptionData exceptionData,
                                 DepartmentSpecifications specifications) {
        this.departmentRepository = departmentRepository;
        this.facultyRepository = facultyRepository;
        this.teacherService = teacherService;
        this.teacherRepository = teacherRepository;
        this.departmentValidator = departmentValidator;
        this.exceptionData = exceptionData;
        this.specifications = specifications;
        className = Department.class.getSimpleName();
    }


    @Override
    public List<Department> findByName(String name) {
        return departmentRepository.getByNameContainingIgnoreCase(name).orElseThrow(()-> new NotFoundException(new ExceptionData<>(className, "name", name)));
    }

    public List<Department> findAllDepartments(FindAllData data) {
        if(data.getFacultyName() == null)
            return findAllWithPaginationOrWithout(data);
        else
            return findAllWithPaginationOrWithoutByFaculty(data);
    }

    public List<Department> findAllWithPaginationOrWithout(FindAllData data) {
        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return departmentRepository.findAll(specifications.getObjectByDeletedCriteria(data.getDeleted()));
        else
            return departmentRepository.findAll(specifications.getObjectByDeletedCriteria(data.getDeleted()), PageRequest.of(data.getPage(), data.getObjectsPerPage())).getContent();
    }

    public List<Department> findAllWithPaginationOrWithoutByFaculty(FindAllData data) {
        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return departmentRepository.findAll(Specification.where(specifications.getDepartmentByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))));
        else
            return departmentRepository.findAll(Specification.where(specifications.getDepartmentByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))), PageRequest.of(data.getPage(), data.getObjectsPerPage())).getContent();
    }

    @Override
    @Transactional
    public void registerNew(RegisterDepartmentData data) {
        checkExistenceByNameBeforeRegistration(new CheckExistsByNameData<>(className, data.getNewDepartment().getName(), departmentRepository));
        validateObject(new DataForAbstractValidator(departmentValidator, data.getNewDepartment()));

        Department newDepartment = data.getNewDepartment();
        newDepartment.setFaculty(facultyRepository.getByName(newDepartment.getFaculty().getName()).get());

        if(newDepartment.getTeachers() != null) {
            for (Teacher teacher: saveDepartmentWithoutTeachers(newDepartment)) {
                teacherService.registerNew(new RegisterPersonData<>(teacher, data.getBindingResult()));
            }
        } else {
            departmentRepository.save(newDepartment);
        }
    }

    public List<Teacher> saveDepartmentWithoutTeachers(Department department) {
        List<Teacher> newTeachers = department.getTeachers();

        department.setTeachers(new ArrayList<>());
        departmentRepository.save(department);

        return newTeachers;
    }

    @Override
    @Transactional
    public void updateByName(UpdateDepartmentData data) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, data.getName(), departmentRepository));
        validateObject(new DataForAbstractValidator(departmentValidator, data.getUpdatedDepartment()));

        Department updatedDepartment = data.getUpdatedDepartment();
        updatedDepartment.setId(departmentRepository.getByName(data.getName()).get().getId());
        updatedDepartment.setFaculty(facultyRepository.getByName(updatedDepartment.getFaculty().getName()).get());
        updatedDepartment.setTeachers(departmentRepository.getByName(data.getName()).get().getTeachers());
        updatedDepartment.setStudentGroups(departmentRepository.getByName(data.getName()).get().getStudentGroups());

        departmentRepository.save(updatedDepartment);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, departmentRepository));
        departmentRepository.deleteByName(name);
    }

    @Override
    public void softDeleteByName(String name) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, departmentRepository));

        Department department = departmentRepository.getByName(name).get();
        department.getStudentGroups().stream().forEach(studentGroup -> studentGroup.setDeleted(true));
        department.getTeachers().stream().forEach(teacher -> teacher.setDeleted(true));
        department.setDeleted(true);

        departmentRepository.save(department);
    }

}

package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.DepartmentService;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.specifications.DeletedSpecification;
import ua.dgma.electronicDeansOffice.services.specifications.impl.SpecificationsImpl;
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
    private final DeletedSpecification specification;
    private String className;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 FacultyRepository facultyRepository,
                                 PeopleService<Teacher> teacherService,
                                 TeacherRepository teacherRepository,
                                 AbstractValidator departmentValidator,
                                 ExceptionData exceptionData,
                                 DeletedSpecification specification) {
        this.departmentRepository = departmentRepository;
        this.facultyRepository = facultyRepository;
        this.teacherService = teacherService;
        this.teacherRepository = teacherRepository;
        this.departmentValidator = departmentValidator;
        this.exceptionData = exceptionData;
        this.specification = specification;
        className = Department.class.getSimpleName();
    }


    @Override
    public Department findById(Long id) {
        return departmentRepository.findById(id).orElseThrow(()-> new NotFoundException(new ExceptionData<>(className, "id", id)));
    }

    @Override
    public Department findByName(String name) {
        return departmentRepository.getByName(name).orElseThrow(()-> new NotFoundException(new ExceptionData<>(className, "name", name)));
    }

    @Override
    public List<Department> findAllWithPaginationOrWithout(Integer page, Integer departmentsPerPage, Boolean isDeleted) {
        if(checkPaginationParameters(page, departmentsPerPage))
            return departmentRepository.findAll(specification.getObjectByDeletedCriteria(isDeleted));
        else
            return departmentRepository.findAll(specification.getObjectByDeletedCriteria(isDeleted), PageRequest.of(page, departmentsPerPage)).getContent();
    }

    @Override
    public List<Department> findAllDepartmentsByFacultyName(String facultyName) {
        checkExistsWithSuchName(new CheckExistsByNameData(Faculty.class.getSimpleName(), facultyName, facultyRepository));
        return departmentRepository.getAllByFacultyName(facultyName);
    }

    @Override
    @Transactional
    public void registerNew(Department department, BindingResult bindingResult) {
        checkExistenceByNameBeforeRegistration(new CheckExistsByNameData<>(Department.class.getSimpleName(), department.getName(), departmentRepository));
        validateObject(new DataForAbstractValidator(departmentValidator, department));

        department.setFaculty(facultyRepository.getByName(department.getFaculty().getName()).get());

        if(department.getTeachers() != null) {
            for (Teacher teacher: saveDepartmentWithoutTeachers(department)) {
                teacherService.registerNew(teacher, bindingResult);
            }
        } else {
            departmentRepository.save(department);
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
    public void updateByName(String name, Department updatedDepartment, BindingResult bindingResult) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, departmentRepository));
        validateObject(new DataForAbstractValidator(departmentValidator, updatedDepartment));

        updatedDepartment.setId(departmentRepository.getByName(name).get().getId());
        updatedDepartment.setFaculty(facultyRepository.getByName(updatedDepartment.getFaculty().getName()).get());
        updatedDepartment.setTeachers(departmentRepository.getByName(name).get().getTeachers());
        updatedDepartment.setStudentGroups(departmentRepository.getByName(name).get().getStudentGroups());

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

        Department department = findByName(name);
        department.getStudentGroups().stream().forEach(studentGroup -> studentGroup.setDeleted(true));
        department.getTeachers().stream().forEach(teacher -> teacher.setDeleted(true));
        department.setDeleted(true);

        departmentRepository.save(department);
    }

}

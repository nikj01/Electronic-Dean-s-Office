package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.ExceptionData;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.DepartmentService;
import ua.dgma.electronicDeansOffice.services.specifications.Specifications;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByNameData;
import ua.dgma.electronicDeansOffice.utill.validators.DepartmentValidator;

import java.util.List;


import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.*;

@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentValidator departmentValidator;
    private final ExceptionData exceptionData;
    private final Specifications<Department> specifications;
    private String className;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 FacultyRepository facultyRepository,
                                 DepartmentValidator departmentValidator,
                                 ExceptionData exceptionData,
                                 Specifications<Department> specifications) {
        this.departmentRepository = departmentRepository;
        this.facultyRepository = facultyRepository;
        this.departmentValidator = departmentValidator;
        this.exceptionData = exceptionData;
        this.specifications = specifications;
        className = Department.class.getSimpleName();
    }


    @Override
    public Department findById(Long id) {
        return departmentRepository.getById(id).orElseThrow(()-> new NotFoundException(new ExceptionData<>(className, "id", id)));
    }

    @Override
    public Department findByName(String name) {
        return departmentRepository.getByName(name).orElseThrow(()-> new NotFoundException(new ExceptionData<>(className, "name", name)));
    }

    @Override
    public List<Department> findAllWithPaginationOrWithout(Integer page, Integer departmentsPerPage, Boolean isDeleted) {
        if(checkPaginationParameters(page, departmentsPerPage))
            return departmentRepository.findAll(specifications.getObjectByDeletedCriteria(isDeleted));
        else
            return departmentRepository.findAll(specifications.getObjectByDeletedCriteria(isDeleted), PageRequest.of(page, departmentsPerPage)).getContent();
    }

    @Override
    public List<Department> findAllDepartmentsByFacultyName(String facultyName) {
        checkExistsWithSuchName(new CheckExistsByNameData(Faculty.class.getSimpleName(), facultyName, facultyRepository));
        return departmentRepository.getAllByFacultyName(facultyName);
    }

    @Override
    @Transactional
    public void registerNew(Department department, BindingResult bindingResult) {
        validateObject(new ValidationData<>(departmentValidator, department, bindingResult));
        departmentRepository.save(department);
    }

    @Override
    @Transactional
    public void updateByName(String name, Department updatedDepartment, BindingResult bindingResult) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, departmentRepository));
        validateObject(new ValidationData<>(departmentValidator, updatedDepartment, bindingResult));

        updatedDepartment.setName(name);
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
        department.setDeleted(true);

        departmentRepository.save(department);
    }

}

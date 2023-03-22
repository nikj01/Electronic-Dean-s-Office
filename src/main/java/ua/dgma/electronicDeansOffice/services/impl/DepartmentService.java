package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.people.ExceptionData;
import ua.dgma.electronicDeansOffice.exceptions.people.NotFoundException;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.utill.validators.DepartmentValidator;

import java.util.List;
import java.util.Set;


import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkPaginationParameters;

@Service
@Transactional(readOnly = true)
public class DepartmentService implements ua.dgma.electronicDeansOffice.services.interfaces.DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentValidator departmentValidator;
    private final ExceptionData exceptionData;
    private String className;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository,
                             DepartmentValidator departmentValidator,
                             ExceptionData exceptionData) {
        this.departmentRepository = departmentRepository;
        this.departmentValidator = departmentValidator;
        this.exceptionData = exceptionData;
        className = Department.class.getSimpleName();
    }


    @Override
    public Department findByName(String name) {
        return departmentRepository.getByName(name).orElseThrow(()-> new NotFoundException(new ExceptionData<String >(className, "name", name)));
    }

    @Override
    public List<Department> findAllWithPaginationOrWithout(Integer page, Integer peoplePerPage) {
        if(checkPaginationParameters(page, peoplePerPage))
            return departmentRepository.findAll();
        else
            return departmentRepository.findAll(PageRequest.of(page, peoplePerPage)).getContent();
    }

    public Set<Department> findAllDepartmentsByFacultyName(String facultyName) {
        return departmentRepository.getAllByFacultyName(facultyName);
    }

    @Override
    public void registerNew(Department department, BindingResult bindingResult) {

    }

    @Override
    public void updateByName(String name, Department updatedDepartment, BindingResult bindingResult) {

    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public void validateDepartment(Department department, BindingResult bindingResult) {

    }

    @Override
    public void checkExistsWithSuchName(String name) {

    }
}

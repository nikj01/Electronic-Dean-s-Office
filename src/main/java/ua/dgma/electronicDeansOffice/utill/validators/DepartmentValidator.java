package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;

@Component
public class DepartmentValidator implements Validator {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;

    @Autowired
    public DepartmentValidator(DepartmentRepository departmentRepository, FacultyRepository facultyRepository) {
        this.departmentRepository = departmentRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {

        return Department.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Department department = (Department) target;

        if(departmentRepository.getByName(department.getName()).isPresent())
            errors.rejectValue("name", "Department with NAME " + department.getName() + " already exists!");
        if(!facultyRepository.getByName(department.getFaculty().getName()).isPresent())
            errors.rejectValue("faculty", "Faculty with NAME " + department.getFaculty().getName() + " does not exist!");
    }
}

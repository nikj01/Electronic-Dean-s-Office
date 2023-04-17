package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;

import java.util.Optional;

@Component
public class TeacherValidator implements Validator {
    private final PeopleRepository<Person> peopleRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public TeacherValidator(PeopleRepository<Person> peopleRepository,
                            DepartmentRepository departmentRepository) {
        this.peopleRepository = peopleRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Teacher.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Teacher teacher = (Teacher) target;

        if (checkTeacherId(teacher)) {
            checkExistenceOfTheDepartment(teacher, errors);
        } else {
            checkExistenceOfThePersonById(teacher, errors);
            checkExistenceOfTheDepartment(teacher, errors);
        }
    }

    private boolean checkTeacherId(Teacher teacher) {
        if (teacher.getUid() == null) return true;
        else return false;
    }

    private void checkExistenceOfThePersonById(Teacher teacher, Errors errors) {
        if (findPerson(teacher).isPresent())
            errors.rejectValue("uid", "Person with UID " + getTeacherId(teacher) + " already exists!");
    }

    private Optional<Person> findPerson(Teacher teacher) {
        return peopleRepository.findById(getTeacherId(teacher));
    }

    private Long getTeacherId(Teacher teacher) {
        return teacher.getUid();
    }

    private void checkExistenceOfTheDepartment(Teacher teacher, Errors errors) {
        if (!findDepartment(teacher).isPresent())
            errors.rejectValue("department", "Department with name " + getDepartmentName(teacher) + " and Id " + getDepartmentId(teacher) + " does not exist!");
    }

    private Optional<Department> findDepartment(Teacher teacher) {
        return departmentRepository.getByName(getDepartmentName(teacher));
    }

    private Long getDepartmentId(Teacher teacher) {
        return getDepartment(teacher).getId();
    }

    private String getDepartmentName(Teacher teacher) {
        return getDepartment(teacher).getName();
    }

    private Department getDepartment(Teacher teacher) {
        return teacher.getDepartment();
    }
}

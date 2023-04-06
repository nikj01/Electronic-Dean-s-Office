package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.exceptions.IncorrectPropertyException;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.utill.validators.data.TeacherValidationData;

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
        TeacherValidationData validationData = new TeacherValidationData(teacher, peopleRepository, departmentRepository, errors);

        if(checkTeacherId(validationData)) {
            checkExistenceOfTheDepartment(validationData);
        } else {
            checkExistenceOfThePersonById(validationData);
            checkExistenceOfTheDepartment(validationData);
        }
    }

    private boolean checkTeacherId(TeacherValidationData data) {
        if(data.getTeacher().getUid() == null) return true; else return false;
    }

    private void checkExistenceOfThePersonById(TeacherValidationData data) {
        if(data.getPeopleRepository().getByUid(data.getTeacher().getUid()).isPresent())
            data.getErrors().rejectValue("uid", "Person with UID " + data.getTeacher().getUid() + " already exists!");
    }

    private void checkExistenceOfTheDepartment(TeacherValidationData data) {
        if(!data.getDepartmentRepository().getByName(data.getTeacher().getDepartment().getName()).isPresent())
            data.getErrors().rejectValue("department", "Department with name " + data.getTeacher().getDepartment().getName() + " does not exist!");
//            throw new IncorrectPropertyException("Department with name " + data.getTeacher().getDepartment().getName() + " does not exist!");
    }
}

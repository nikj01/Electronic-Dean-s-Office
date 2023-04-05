package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;
import ua.dgma.electronicDeansOffice.utill.validators.data.StudentValidationData;

@Component
public class StudentValidator implements Validator {

    private final PeopleRepository<Person> peopleRepository;
    private final StudentRepository studentRepository;
    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentValidator(PeopleRepository<Person> peopleRepository,
                            StudentRepository studentRepository,
                            StudentGroupRepository studentGroupRepository) {
        this.peopleRepository = peopleRepository;
        this.studentRepository = studentRepository;
        this.studentGroupRepository = studentGroupRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Student student = (Student) target;
        StudentValidationData validationData = new StudentValidationData(student, peopleRepository, studentRepository, studentGroupRepository, errors);

        if(checkStudentsUid(validationData)) {
            checkExistenceOfTheStudentGroup(validationData);
        } else {
            checkExistenceOfThePersonById(validationData);
            checkExistenceOfTheStudentGroup(validationData);
        }
    }
    private boolean checkStudentsUid(StudentValidationData data) {
        if(data.getStudent().getUid() == null) return true; else return false;
    }

    private void checkExistenceOfThePersonById(StudentValidationData data) {
        if(data.getPeopleRepository().getByUid(data.getStudent().getUid()).isPresent())
            data.getErrors().rejectValue("uid", "Person with UID " + data.getStudent().getUid() + " already exists!");
    }

    private void checkExistenceOfTheStudentGroup(StudentValidationData data) {
        if(!data.getStudentGroupRepository().getByName(data.getStudent().getStudentGroup().getName()).isPresent())
            data.getErrors().rejectValue("studentGroup", "Student group with name " + data.getStudent().getStudentGroup().getName() + " does not exist!");
    }
}

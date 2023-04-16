package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;

import java.util.Optional;

@Component
public class StudentValidator implements Validator {
    private final PeopleRepository<Person> peopleRepository;
    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentValidator(PeopleRepository<Person> peopleRepository,
                            StudentGroupRepository studentGroupRepository) {
        this.peopleRepository = peopleRepository;
        this.studentGroupRepository = studentGroupRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Student student = (Student) target;

        if (checkStudentsUid(student)) {
            checkExistenceOfTheStudentGroup(student, errors);
        } else {
            checkExistenceOfThePersonById(student, errors);
            checkExistenceOfTheStudentGroup(student, errors);
        }
    }

    private boolean checkStudentsUid(Student student) {
        if (student.getUid() == null) return true;
        else return false;
    }

    private void checkExistenceOfThePersonById(Student student, Errors errors) {
        if (findStudent(student).isPresent())
            errors.rejectValue("uid", "Person with UID " + getStudentId(student) + " already exists!");
    }

    private Optional<Person> findStudent(Student student) {
        return peopleRepository.findById(getStudentId(student));
    }

    private Long getStudentId(Student student) {
        return student.getUid();
    }

    private void checkExistenceOfTheStudentGroup(Student student, Errors errors) {
        if (!findGroup(student).isPresent())
            errors.rejectValue("studentGroup", "Student group with name " + getGroupName(student) + " and Id " + getGroupId(student) + " does not exist!");
    }

    private Optional<StudentGroup> findGroup(Student student) {
        return studentGroupRepository.findById(getGroupId(student));
    }

    private String getGroupName(Student student) {
        return getGroupFromStudent(student).getName();
    }

    private Long getGroupId(Student student) {
        return getGroupFromStudent(student).getId();
    }

    private StudentGroup getGroupFromStudent(Student student) {
        return student.getStudentGroup();
    }

}

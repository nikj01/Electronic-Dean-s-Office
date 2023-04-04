package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.utill.check.CheckExistenceStudentGroup;

import static ua.dgma.electronicDeansOffice.utill.check.CheckExistenceStudentGroup.checkExistenceStudentGroup;

@Component
public class StudentValidator implements Validator {

    private final PeopleRepository<Student> studentRepository;
    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentValidator(PeopleRepository<Student> studentRepository,
                            StudentGroupRepository studentGroupRepository) {
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

        if(studentRepository.getByUid(student.getUid()).isPresent())
            errors.rejectValue("uid", "Student with UID " + student.getUid() + " already exists!");
        if(!studentGroupRepository.getByName(student.getStudentGroup().getName()).isPresent())
            errors.rejectValue("student_group", "Student group with NAME " + student.getStudentGroup().getName() + " does not exist!");
    }
}

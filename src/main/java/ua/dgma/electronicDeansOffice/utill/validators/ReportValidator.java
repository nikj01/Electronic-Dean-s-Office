package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Report;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.repositories.EventRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;

import java.util.Optional;
import java.util.Set;

@Component
public class ReportValidator implements Validator {
    private final EventRepository eventRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public ReportValidator(EventRepository eventRepository,
                           StudentRepository studentRepository) {
        this.eventRepository = eventRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Report.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Report report = (Report) target;

        if (checkExistenceOfTheReport(report))
            checkExistenceOfTheStudents(report, errors);
    }

    private boolean checkExistenceOfTheReport(Report report) {
        if (report.getId() == null) return true;
        else return false;
    }

    private void checkExistenceOfTheStudents(Report report, Errors errors) {
        for (Student student : getStudentsFromReport(report))
            checkExistenceOfTheStudent(student, errors);
    }

    private Set<Student> getStudentsFromReport(Report report) {
        return report.getStudentAttendance().keySet();
    }

    private void checkExistenceOfTheStudent(Student student, Errors errors) {
        if (!findStudent(student).isPresent())
            errors.rejectValue("student", "Student with surname " + getStudentSurname(student) + " and Id " + getStudentId(student) + " does not exist!");
    }

    private Optional<Student> findStudent(Student student) {
        return studentRepository.findById(getStudentId(student));
    }

    private Long getStudentId(Student student) {
        return student.getUid();
    }

    private String getStudentSurname(Student student) {
        return student.getSurname();
    }
}


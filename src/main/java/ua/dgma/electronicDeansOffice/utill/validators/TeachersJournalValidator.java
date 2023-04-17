package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;

import java.util.Optional;

@Component
public class TeachersJournalValidator implements Validator {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeachersJournalValidator(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TeachersJournal.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TeachersJournal journal = (TeachersJournal) target;

        if (!checkExistenceOfTheJournal(journal))
            checkExistenceOfTheTeacher(journal, errors);
    }

    private boolean checkExistenceOfTheJournal(TeachersJournal journal) {
        if (journal.getId() == null) return true;
        else return false;
    }

    private void checkExistenceOfTheTeacher(TeachersJournal journal, Errors errors) {
        if (!findTeacher(journal).isPresent())
            errors.rejectValue("teacher", "Teacher with uid " + getTeacherId(journal) + " does not exist!");
    }

    private Optional<Teacher> findTeacher(TeachersJournal journal) {
        return teacherRepository.findById(getTeacherId(journal));
    }

    private Long getTeacherId(TeachersJournal journal) {
        return getTeacherFromJournal(journal).getUid();
    }

    private Teacher getTeacherFromJournal(TeachersJournal journal) {
        return journal.getTeacher();
    }
}

package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.repositories.TeachersJournalRepository;
import ua.dgma.electronicDeansOffice.utill.validators.data.TeachersJournalValidationData;

import java.util.Optional;

@Component
public class TeachersJournalValidator implements Validator {
    private final TeachersJournalRepository journalRepository;
    private final TeacherRepository teacherRepository;


    @Autowired
    public TeachersJournalValidator(TeachersJournalRepository journalRepository,
                                    TeacherRepository teacherRepository) {
        this.journalRepository = journalRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TeachersJournal.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TeachersJournal journal = (TeachersJournal) target;
        TeachersJournalValidationData validationData = new TeachersJournalValidationData(journal, journalRepository, teacherRepository, errors);

        if(!checkExistenceOfTheJournal(validationData))
            checkExistenceOfTheTeacher(validationData);
    }

    private boolean checkExistenceOfTheJournal(TeachersJournalValidationData data) {
        if(data.getTeachersJournal().getId() == null) return true; else return false;
    }

    private void checkExistenceOfTheTeacher(TeachersJournalValidationData data) {
        if(!findTeacher(data).isPresent())
            data.getErrors().rejectValue("teacher", "Teacher with uid " + getTeacherFromData(data).getUid() + " does not exist!");
    }

    private Teacher getTeacherFromData(TeachersJournalValidationData data) {
        return data.getTeachersJournal().getTeacher();
    }

    private Optional<Teacher> findTeacher(TeachersJournalValidationData data) {
        return data.getTeacherRepository().getByUid(getTeacherFromData(data).getUid());
    }
}

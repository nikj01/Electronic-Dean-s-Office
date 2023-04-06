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
    private final TeachersJournalValidationData data;


    @Autowired
    public TeachersJournalValidator(TeachersJournalRepository journalRepository,
                                    TeacherRepository teacherRepository,
                                    TeachersJournalValidationData data) {
        this.journalRepository = journalRepository;
        this.teacherRepository = teacherRepository;
        this.data = data;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TeachersJournal.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TeachersJournal journal = (TeachersJournal) target;

        if(checkExistenceOfTheJournal(validationData)) {
            checkExistenceOfTheTeacher(validationData);
        } else {
            findTeachersJournal(validationData);
        }
    }

    private TeachersJournalValidationData getData(TeachersJournalValidationData data) {
        return data;
    }

    private boolean checkExistenceOfTheJournal(TeachersJournalValidationData data) {
        if(data.getTeachersJournal().getId() == null) return true; else return false;
    }

    private Optional<TeachersJournal> findTeachersJournal(TeachersJournalValidationData data) {
        return journalRepository.findById(data.getTeachersJournal().getId());
    }

    private void checkExistenceOfTheTeacher(TeachersJournalValidationData data) {
        if(!findTeacher(data).isPresent())
            data.getErrors().rejectValue("teacher", "Teacher with uid " + getTeacherFromData().getUid() + " does not exist!");
    }
    private Teacher getTeacherFromData() {
        return data.getTeachersJournal().getTeacher();
    }

    private Optional<Teacher> findTeacher(TeachersJournalValidationData data) {
        return teacherRepository.getByUid(findTeachersJournal(data).get().getTeacher().getUid());
    }
}

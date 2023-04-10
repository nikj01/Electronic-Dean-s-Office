package ua.dgma.electronicDeansOffice.utill.validators.data;

import lombok.*;
import org.springframework.validation.Errors;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.repositories.TeachersJournalRepository;

@Getter
@Setter
@RequiredArgsConstructor
public class TeachersJournalValidationData {
    @NonNull
    private TeachersJournal teachersJournal;
    @NonNull
    private TeachersJournalRepository journalRepository;
    @NonNull
    private TeacherRepository teacherRepository;
    @NonNull
    private Errors errors;
}

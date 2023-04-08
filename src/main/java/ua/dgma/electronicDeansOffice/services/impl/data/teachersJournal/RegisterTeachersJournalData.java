package ua.dgma.electronicDeansOffice.services.impl.data.teachersJournal;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Teacher;

@Data
@RequiredArgsConstructor
public class RegisterTeachersJournalData {
    @NonNull
    private Teacher newTeacher;
    @NonNull
    private BindingResult bindingResult;
}

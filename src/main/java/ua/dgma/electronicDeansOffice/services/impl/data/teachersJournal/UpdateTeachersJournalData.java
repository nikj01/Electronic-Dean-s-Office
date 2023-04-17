package ua.dgma.electronicDeansOffice.services.impl.data.teachersJournal;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;

@Data
@RequiredArgsConstructor
public class UpdateTeachersJournalData {
    @NonNull
    private Long id;
    @NonNull
    private TeachersJournal updatedJournal;
    @NonNull
    private BindingResult bindingResult;
    private Teacher updatedTeacher;

    public UpdateTeachersJournalData(@NonNull Long id,
                                     @NonNull TeachersJournal updatedJournal,
                                     @NonNull BindingResult bindingResult,
                                     Teacher updatedTeacher) {
        this.id = id;
        this.updatedJournal = updatedJournal;
        this.bindingResult = bindingResult;
        this.updatedTeacher = updatedTeacher;
    }
}

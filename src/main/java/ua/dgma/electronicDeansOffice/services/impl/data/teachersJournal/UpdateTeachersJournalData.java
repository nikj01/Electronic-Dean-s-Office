package ua.dgma.electronicDeansOffice.services.impl.data.teachersJournal;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
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
}

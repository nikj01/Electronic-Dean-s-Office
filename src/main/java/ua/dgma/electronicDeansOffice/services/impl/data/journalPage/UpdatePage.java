package ua.dgma.electronicDeansOffice.services.impl.data.journalPage;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.dgma.electronicDeansOffice.models.JournalPage;

@Data
@RequiredArgsConstructor
public class UpdatePage {
    @NonNull
    private JournalPage existingPage;
    @NonNull
    private JournalPage updatedPage;
}

package ua.dgma.electronicDeansOffice.services.impl.data.journalPage;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.JournalPage;

@Data
@RequiredArgsConstructor
public class RegisterJournalPageData {
    @NonNull
    private JournalPage newJournalPage;
    @NonNull
    private BindingResult bindingResult;

}

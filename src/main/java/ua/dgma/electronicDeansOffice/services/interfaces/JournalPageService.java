package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.JournalPage;
import ua.dgma.electronicDeansOffice.services.impl.data.journalPage.RegisterJournalPageData;
import ua.dgma.electronicDeansOffice.services.impl.data.journalPage.UpdateJournalPageData;

import java.util.Optional;

public interface JournalPageService {

    JournalPage findById(Long id);
    void registerNew(RegisterJournalPageData data);
    void updateById(UpdateJournalPageData data);
    void deleteById(Long id);
}

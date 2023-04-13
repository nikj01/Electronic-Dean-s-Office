package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.JournalPage;
import ua.dgma.electronicDeansOffice.services.impl.data.journalPage.RegisterJournalPageData;
import ua.dgma.electronicDeansOffice.services.impl.data.journalPage.UpdateJournalPageData;

public interface JournalPageService {
    JournalPage findOne(Long pageId);
    void register(RegisterJournalPageData data);
    void update(UpdateJournalPageData data);
    void delete(Long pageId);
}

package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.teachersJournal.RegisterTeachersJournalData;
import ua.dgma.electronicDeansOffice.services.impl.data.teachersJournal.UpdateTeachersJournalData;

import java.util.List;

public interface TeachersJournalService {

    TeachersJournal findByid(Long id);
    List<TeachersJournal> findByComment(String comment);
    List<TeachersJournal> findAll(FindAllData data);
    void registerNew(RegisterTeachersJournalData data);
    void update(UpdateTeachersJournalData data);
    void delete(Long id);
    void softDelete(Long id);
}

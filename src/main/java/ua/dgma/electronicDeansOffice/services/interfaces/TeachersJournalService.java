package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.teachersJournal.RegisterTeachersJournalData;
import ua.dgma.electronicDeansOffice.services.impl.data.teachersJournal.UpdateTeachersJournalData;

import java.util.List;

public interface TeachersJournalService {
    TeachersJournal findOne(Long journalId);
    TeachersJournal findOneByTeacher(Long teacherId);
    List<TeachersJournal> findAll(FindAllData data);
    void register(RegisterTeachersJournalData data);
    void delete(Long journalId);
    void softDelete(Long journalId);
}

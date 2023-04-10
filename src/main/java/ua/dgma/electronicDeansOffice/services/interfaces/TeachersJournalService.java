package ua.dgma.electronicDeansOffice.services.interfaces;

import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.teachersJournal.RegisterTeachersJournalData;
import ua.dgma.electronicDeansOffice.services.impl.data.teachersJournal.UpdateTeachersJournalData;

import java.util.List;
import java.util.Optional;

public interface TeachersJournalService {

    TeachersJournal findByid(Long id);
    List<TeachersJournal> findByComment(String comment);
    List<TeachersJournal> findAll(FindAllData data);
    void registerNew(RegisterTeachersJournalData data);
    void updateById(UpdateTeachersJournalData data);
    void deleteById(Long id);
    void softDeleteById(Long id);
    void removeTeachersFromJournals(List<Teacher> teachers);
    void markTeachersJournalsAsDeleted(List<Teacher> teachers);
}

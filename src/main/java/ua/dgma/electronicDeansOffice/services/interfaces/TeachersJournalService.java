package ua.dgma.electronicDeansOffice.services.interfaces;

import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;

import java.util.List;
import java.util.Optional;

public interface TeachersJournalService {

    TeachersJournal findByid(Long id);
    TeachersJournal findByTeacherUid(Long uid);
    List<TeachersJournal> findAllWithPaginationOrWithout(Integer page, Integer journalsPerPage, Boolean isDeleted);
    void registerNew(TeachersJournal teachersJournal, BindingResult bindingResult);
    void updateById(Long id, TeachersJournal updatedJournal, BindingResult bindingResult);
    void deleteById(Long id);
    void softDeleteById(Long id);
}

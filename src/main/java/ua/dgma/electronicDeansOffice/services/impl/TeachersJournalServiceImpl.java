package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.repositories.TeachersJournalRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.TeachersJournalService;

import java.util.List;

@Service
@Transactional
public class TeachersJournalServiceImpl implements TeachersJournalService {

    private final TeachersJournalRepository teachersJournalRepository;

    public TeachersJournalServiceImpl(TeachersJournalRepository teachersJournalRepository) {
        this.teachersJournalRepository = teachersJournalRepository;
    }

    @Override
    public TeachersJournal findByid(Long id) {
        return null;
    }

    @Override
    public TeachersJournal findByTeacherUid(Long uid) {
        return null;
    }

    @Override
    public List<TeachersJournal> findAllWithPaginationOrWithout(Integer page, Integer journalsPerPage, Boolean isDeleted) {
        return null;
    }

    @Override
    public void registerNew(TeachersJournal teachersJournal, BindingResult bindingResult) {

    }

    @Override
    public void updateById(Long id, TeachersJournal updatedJournal, BindingResult bindingResult) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void softDeleteById(Long id) {

    }
}

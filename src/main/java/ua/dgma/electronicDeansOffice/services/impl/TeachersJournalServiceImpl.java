package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.repositories.TeachersJournalRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.TeachersJournalService;
import ua.dgma.electronicDeansOffice.services.specifications.DeletedSpecification;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.data.DataForAbstractValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceByIDBeforeRegistration;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkPaginationParameters;

@Service
@Transactional
public class TeachersJournalServiceImpl implements TeachersJournalService {

    private final TeachersJournalRepository teachersJournalRepository;
    private final TeacherRepository teacherRepository;
    private final DeletedSpecification specification;

    public TeachersJournalServiceImpl(TeachersJournalRepository teachersJournalRepository,
                                      TeacherRepository teacherRepository,
                                      DeletedSpecification specification) {
        this.teachersJournalRepository = teachersJournalRepository;
        this.teacherRepository = teacherRepository;
        this.specification = specification;
    }

    @Override
    public TeachersJournal findByid(Long id) {
        return teachersJournalRepository.findById(id).orElseThrow(() -> new NotFoundException(new ExceptionData<>(TeachersJournal.class.getSimpleName(), "id", id)));
    }

    @Override
    public TeachersJournal findByTeacherUid(Long uid) {
        return teachersJournalRepository.getByTeacher_Uid(uid).orElseThrow(() -> new NotFoundException(new ExceptionData<>(TeachersJournal.class.getSimpleName(), "teachers_id", uid)));
    }

    @Override
    public List<TeachersJournal> findAllWithPaginationOrWithout(Integer page, Integer journalsPerPage, Boolean isDeleted) {
        if(checkPaginationParameters(page, journalsPerPage))
            return teachersJournalRepository.findAll(Specification.where(specification.getObjectByDeletedCriteria(isDeleted)));
        else
            return teachersJournalRepository.findAll(Specification.where(specification.getObjectByDeletedCriteria(isDeleted)), PageRequest.of(page, journalsPerPage)).getContent();
    }

    @Override
    public void registerNew(TeachersJournal teachersJournal, BindingResult bindingResult) {
        checkExistenceByIDBeforeRegistration(new CheckExistsByIdData<>(TeachersJournal.class.getSimpleName(), teachersJournal.getId(), teachersJournalRepository));
        validateObject(new DataForAbstractValidator())
        teachersJournal.setTeacher(teacherRepository.getByUid(teachersJournal.getTeacher().getUid()).get());

        teachersJournalRepository.save(teachersJournal);
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

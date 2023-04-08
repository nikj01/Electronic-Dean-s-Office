package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.repositories.JournalPageRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.repositories.TeachersJournalRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.TeachersJournalService;
import ua.dgma.electronicDeansOffice.services.specifications.DeletedSpecification;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.TeachersJournalValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.*;

@Service
@Transactional
public class TeachersJournalServiceImpl implements TeachersJournalService {

    private final TeachersJournalRepository journalRepository;
    private final TeacherRepository teacherRepository;
    private final JournalPageRepository pageRepository;
    private final DeletedSpecification specification;
    private final TeachersJournalValidator journalValidator;
    private String className;

    @Autowired
    public TeachersJournalServiceImpl(TeachersJournalRepository journalRepository,
                                      TeacherRepository teacherRepository,
                                      JournalPageRepository pageRepository,
                                      DeletedSpecification specification,
                                      TeachersJournalValidator journalValidator) {
        this.journalRepository = journalRepository;
        this.teacherRepository = teacherRepository;
        this.pageRepository = pageRepository;
        this.specification = specification;
        this.journalValidator = journalValidator;
        this.className = TeachersJournal.class.getSimpleName();
    }

    @Override
    public TeachersJournal findByid(Long id) {
        return journalRepository.findById(id).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "id", id)));
    }

    @Override
    public TeachersJournal findByTeacherUid(Long uid) {
        return journalRepository.getByTeacher_Uid(uid).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "teachers_id", uid)));
    }
    @Override
    public TeachersJournal findByComment(String comment) {
        return journalRepository.getByCommentContaining(comment).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "comment", comment)));
    }

    @Override
    public List<TeachersJournal> findAll(Integer page, Integer journalsPerPage, Boolean isDeleted, String faculutyName) {
        if(faculutyName == null)
            return findAllWithPaginationOrWithout(page, journalsPerPage, isDeleted);
        else
            return findAllWithPaginationOrWithoutByFaculty(page, journalsPerPage, isDeleted, faculutyName);
    }

    public List<TeachersJournal> findAllWithPaginationOrWithout(Integer page, Integer journalsPerPage, Boolean isDeleted) {
        if(checkPaginationParameters(page, journalsPerPage))
            return journalRepository.findAll(Specification.where(specification.getObjectByDeletedCriteria(isDeleted)));
        else
            return journalRepository.findAll(Specification.where(specification.getObjectByDeletedCriteria(isDeleted)), PageRequest.of(page, journalsPerPage)).getContent();
    }

    public List<TeachersJournal> findAllWithPaginationOrWithoutByFaculty(Integer page, Integer journalsPerPage, Boolean isDeleted, String faculutyName) {
        return null;
    }

    @Override
    public void registerNew(Teacher teacher, BindingResult bindingResult) {
        checkExistenceByIDBeforeRegistration(new CheckExistsByIdData<>(teacherClassName(), teacher.getUid(), journalRepository));
//        validateObject(new ValidationData<>(journalValidator, teacher, bindingResult));

        TeachersJournal newJournal = new TeachersJournal();
        setTeacher(newJournal, teacher);
        setJournalComment(newJournal, teacher);

        journalRepository.save(newJournal);
    }

    private String teacherClassName() {
        return Teacher.class.getSimpleName();
    }

    @Override
    public void updateById(Long id, TeachersJournal updatedJournal, BindingResult bindingResult) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, updatedJournal, journalRepository));
        validateObject(new ValidationData<>(journalValidator, updatedJournal, bindingResult));

        updatedJournal.setId(id);
        updatedJournal.setPages(pageRepository.findAllByJournal_Id(id));
        setTeacher(updatedJournal);
        setJournalComment(updatedJournal);

        journalRepository.save(updatedJournal);
    }

    private void setJournalComment(TeachersJournal journal) {
        journal.setComment("Personal journal of the teacher " + journal.getTeacher().getSurname() + " " + journal.getTeacher().getName() + " " + journal.getTeacher().getPatronymic());
    }

    private void setJournalComment(TeachersJournal journal, Teacher teacher) {
        journal.setComment("Personal journal of the teacher " + teacher.getSurname() + " " + teacher.getName() + " " + teacher.getPatronymic());
    }

    private void setTeacher(TeachersJournal journal) {
        journal.setTeacher(teacherRepository.getByUid(journal.getTeacher().getUid()).get());
    }

    private void setTeacher(TeachersJournal journal, Teacher teacher) {
        journal.setTeacher(teacherRepository.getByUid(teacher.getUid()).get());
    }

    @Override
    public void deleteById(Long id) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, id, journalRepository));
        journalRepository.deleteById(id);
    }

    @Override
    public void softDeleteById(Long id) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, id, journalRepository));

        TeachersJournal journal = findByid(id);
        journal.setDeleted(true);

        journalRepository.save(journal);
    }
}

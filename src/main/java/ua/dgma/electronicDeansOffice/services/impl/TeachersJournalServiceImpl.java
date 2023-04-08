package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.repositories.JournalPageRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.repositories.TeachersJournalRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.teachersJournal.RegisterTeachersJournalData;
import ua.dgma.electronicDeansOffice.services.impl.data.teachersJournal.UpdateTeachersJournalData;
import ua.dgma.electronicDeansOffice.services.interfaces.TeachersJournalService;
import ua.dgma.electronicDeansOffice.services.specifications.impl.TeachersJournalSpecifications;
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
    private final TeachersJournalSpecifications specifications;
    private final TeachersJournalValidator journalValidator;
    private String className;

    @Autowired
    public TeachersJournalServiceImpl(TeachersJournalRepository journalRepository,
                                      TeacherRepository teacherRepository,
                                      JournalPageRepository pageRepository,
                                      TeachersJournalSpecifications specifications,
                                      TeachersJournalValidator journalValidator) {
        this.journalRepository = journalRepository;
        this.teacherRepository = teacherRepository;
        this.pageRepository = pageRepository;
        this.specifications = specifications;
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
    public List<TeachersJournal> findByComment(String comment) {
        return journalRepository.getByCommentContainingIgnoreCase(comment).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "comment", comment)));
    }

    @Override
    public List<TeachersJournal> findAll(FindAllData data) {
        if(data.getFacultyName() == null)
            return findAllWithPaginationOrWithout(data);
        else
            return findAllWithPaginationOrWithoutByFaculty(data);
    }

    public List<TeachersJournal> findAllWithPaginationOrWithout(FindAllData data) {
        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return journalRepository.findAll(Specification.where(specifications.getObjectByDeletedCriteria(data.getDeleted())), Sort.by("id"));
        else
            return journalRepository.findAll(Specification.where(specifications.getObjectByDeletedCriteria(data.getDeleted())), PageRequest.of(data.getPage(), data.getObjectsPerPage(), Sort.by("id"))).getContent();
    }

    public List<TeachersJournal> findAllWithPaginationOrWithoutByFaculty(FindAllData data) {
        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return journalRepository.findAll(Specification.where(specifications.getTeacherJournalByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))), Sort.by("id"));
        else
            return journalRepository.findAll(Specification.where(specifications.getTeacherJournalByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))), PageRequest.of(data.getPage(), data.getObjectsPerPage(), Sort.by("id"))).getContent();
    }

    @Override
    public void registerNew(RegisterTeachersJournalData data) {
        checkExistenceByIDBeforeRegistration(new CheckExistsByIdData<>(teacherClassName(), data.getNewTeacher().getUid(), journalRepository));
//        validateObject(new ValidationData<>(journalValidator, teacher, bindingResult));

        Teacher newTeacher = data.getNewTeacher();
        TeachersJournal newJournal = new TeachersJournal();
        setTeacher(newJournal, newTeacher);
        setJournalComment(newJournal, newTeacher);

        journalRepository.save(newJournal);
    }

    private String teacherClassName() {
        return Teacher.class.getSimpleName();
    }

    @Override
    public void updateById(UpdateTeachersJournalData data) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, data.getUpdatedJournal(), journalRepository));
        validateObject(new ValidationData<>(journalValidator, data.getUpdatedJournal(), data.getBindingResult()));

        TeachersJournal updatedJournal = data.getUpdatedJournal();
        updatedJournal.setId(data.getId());
        updatedJournal.setPages(pageRepository.findAllByJournal_Id(data.getId()));
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

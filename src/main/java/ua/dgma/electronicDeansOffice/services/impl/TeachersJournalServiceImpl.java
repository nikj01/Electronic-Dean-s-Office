package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import ua.dgma.electronicDeansOffice.services.interfaces.TeachersJournalService;
import ua.dgma.electronicDeansOffice.services.specifications.TeachersJournalSpecifications;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.TeachersJournalValidator;

import java.time.LocalDateTime;
import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.*;

@Service
@Transactional(readOnly = true)
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
    public TeachersJournal findOne(Long journalId) {
        return journalRepository.findById(journalId).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "id", journalId)));
    }

    @Override
    public TeachersJournal findOneByTeacher(Long teacherId) {
        return journalRepository.getByTeacher_Uid(teacherId).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "teacher_id", teacherId)));
    }

    @Override
    public List<TeachersJournal> findAll(FindAllData data) {
        if (checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return findAllWithSpec(getSpec(data));
        else
            return findAllWithSpecAndPagination(getSpec(data), data);
    }

    private List<TeachersJournal> findAllWithSpec(Specification spec) {
        return journalRepository.findAll(spec);
    }

    private List<TeachersJournal> findAllWithSpecAndPagination(Specification spec, FindAllData data) {
        return journalRepository.findAll(spec, PageRequest.of(data.getPage(), data.getObjectsPerPage())).getContent();
    }

    private Specification getSpec(FindAllData data) {
        return specifications.getSpecForTeacherJournals(data);
    }

    @Override
    @Transactional
    public void register(RegisterTeachersJournalData data) {
        checkExistenceObjectWithSuchIDBeforeRegistrationOrUpdate(new CheckExistsByIdData<>(teacherClassName(), getTeacherId(data), journalRepository));

        Teacher newTeacher = getNewTeacher(data);
        TeachersJournal newJournal = new TeachersJournal();

        setTeacherInNewJournal(newJournal, newTeacher);

        saveTeachersJournal(newJournal);
    }

    private String teacherClassName() {
        return Teacher.class.getSimpleName();
    }

    private Long getTeacherId(RegisterTeachersJournalData data) {
        return getNewTeacher(data).getUid();
    }

    private Teacher getNewTeacher(RegisterTeachersJournalData data) {
        return data.getNewTeacher();
    }

    private void setTeacherInNewJournal(TeachersJournal journal, Teacher teacher) {
        journal.setTeacher(getExistingTeacher(teacher));
    }

    private Teacher getExistingTeacher(Teacher teacher) {
        return teacherRepository.getByUid(getTeacherUid(teacher)).get();
    }

    private Long getTeacherUid(Teacher teacher) {
        return teacher.getUid();
    }

    private void saveTeachersJournal(TeachersJournal journal) {
        journalRepository.save(journal);
    }

    @Override
    @Transactional
    public void delete(Long journalId) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, journalId, journalRepository));

        journalRepository.deleteById(journalId);
    }

    @Override
    @Transactional
    public void softDelete(Long journalId) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, journalId, journalRepository));

        saveTeachersJournal(markTeachersJournalAsDeleted(findOne(journalId)));
    }

    private TeachersJournal markTeachersJournalAsDeleted(TeachersJournal existingJournal) {
        existingJournal.setDeleted(true);
        existingJournal.setWasDeleted(LocalDateTime.now());
        return existingJournal;
    }
}

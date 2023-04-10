package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.repositories.TeachersJournalRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.teachersJournal.RegisterTeachersJournalData;
import ua.dgma.electronicDeansOffice.services.interfaces.TeachersJournalService;
import ua.dgma.electronicDeansOffice.services.specifications.TeacherSpecifications;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.TeacherValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.*;

@Service
@Transactional(readOnly = true)
public class TeacherServiceImpl extends PeopleServiceImpl<Teacher>{

    private final TeacherRepository teacherRepository;
    private final TeachersJournalRepository journalRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentGroupRepository groupRepository;
    private final TeacherValidator teacherValidator;
    private final TeacherSpecifications specifications;
    private final TeachersJournalService journalService;
    private String className;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              TeachersJournalRepository journalRepository,
                              TeacherValidator teacherValidator,
                              ExceptionData exceptionData,
                              DepartmentRepository departmentRepository,
                              StudentGroupRepository groupRepository,
                              TeacherSpecifications specifications,
                              TeachersJournalService journalService) {
        super(teacherRepository, teacherValidator, exceptionData, specifications);
        this.teacherRepository = teacherRepository;
        this.journalRepository = journalRepository;
        this.teacherValidator = teacherValidator;
        this.departmentRepository = departmentRepository;
        this.groupRepository = groupRepository;
        this.specifications = specifications;
        this.journalService = journalService;
        this.className = Teacher.class.getSimpleName();
    }

    @Override
    public List<Teacher> findAllWithPaginationOrWithoutByFaculty(FindAllData data) {
        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return teacherRepository.findAll(Specification.where(specifications.findTeachersByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))), Sort.by("surname"));
        else
            return teacherRepository.findAll(Specification.where(specifications.findTeachersByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))), PageRequest.of(data.getPage(), data.getObjectsPerPage(), Sort.by("surname"))).getContent();
    }

    @Override
    public void registerNew(RegisterPersonData<Teacher> data) {
        checkExistenceByIDBeforeRegistration(new CheckExistsByIdData<>(className, getPersonUid(data), teacherRepository));
        validateObject(new ValidationData<>(teacherValidator, data.getNewPerson(), data.getBindingResult()));

        Teacher newTeacher = data.getNewPerson();

        setDepartmentForTeacher(newTeacher);

        saveTeacher(newTeacher);
        makeNewTeachersJournal(newTeacher, data);
    }

    private Long getPersonUid(RegisterPersonData data) {
        return data.getNewPerson().getUid().longValue();
    }
    private void setDepartmentForTeacher(Teacher teacher) {
        teacher.setDepartment(departmentRepository.getByName(getDepartmentName(teacher)).get());
    }
    private String getDepartmentName(Teacher teacher) {
        return teacher.getDepartment().getName();
    }
    private void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }
    private void makeNewTeachersJournal(Teacher teacher, RegisterPersonData data) {
        journalService.registerNew(new RegisterTeachersJournalData(teacher, data.getBindingResult()));
    }

    @Override
    public void updateByUid(UpdatePersonData<Teacher> data) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, data.getUid(), teacherRepository));
        validateObject(new ValidationData<>(teacherValidator, data.getUpdatedPerson(), data.getBindingResult()));

        Teacher updatedTeacher = data.getUpdatedPerson();

        setIdOnUpdatedTeacher(updatedTeacher, data);
        setDepartmentForTeacher(updatedTeacher);

        saveTeacher(updatedTeacher);
    }

    private void setIdOnUpdatedTeacher(Teacher updatedTeacher, UpdatePersonData data) {
        updatedTeacher.setUid(data.getUid());
    }

    @Override
    public void deleteByUId(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, uid, teacherRepository));

        deleteTeacherFromJournal(getTeachersJournalByTeacherId(uid));

        deleteTeacher(uid);
    }

    private void deleteTeacherFromJournal(TeachersJournal journal) {
        removeTeacher(journal);

        saveJournal(journal);
    }
    private TeachersJournal getTeachersJournalByTeacherId(Long uid) {
        return journalRepository.getByTeacher_Uid(uid).get();
    }

    private void removeTeacher(TeachersJournal journal) {
        journal.setTeacher(null);
    }
    private void saveJournal(TeachersJournal journal) {
        journalRepository.save(journal);
    }
    private void deleteTeacher(Long uid) {
        Teacher teacher = teacherRepository.getByUid(uid).get();
        teacher.getStudentGroups().stream().forEach(studentGroup -> studentGroup.setCurator(null));
        getTeachersJournalByTeacherId(uid).setTeacher(null);
        teacherRepository.deleteByUid(uid);
    }

    @Override
    public void softDeleteByUId(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, uid, teacherRepository));

        markTeachersJournalAsDeleted(uid);
        saveTeacher(markTeacherAsDeleted(findByUid(uid)));
    }
    private Teacher markTeacherAsDeleted(Teacher teacher) {
        teacher.setDeleted(true);
        return teacher;
    }
    private void markTeachersJournalAsDeleted(Long teachersUid) {
        journalService.softDeleteById(getTeachersJournalByTeacherId(teachersUid).getId());
    }

    @Override
    public void markPeopleAsDeleted(List<Teacher> people) {
        for (Teacher teacher : people)
            markTeacherAsDeleted(teacher);
    }
}

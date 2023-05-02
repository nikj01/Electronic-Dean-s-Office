package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.EventData;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.EventDataRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
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
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceObjectWithSuchID;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceObjectWithSuchIDBeforeRegistrationOrUpdate;

@Service
@Transactional(readOnly = true)
public class TeacherServiceImpl extends PeopleServiceImpl<Teacher> {
    private final TeacherRepository teacherRepository;
    private final TeachersJournalService journalService;
    private final DepartmentRepository departmentRepository;
    private final EventDataRepository dataRepository;
    private final TeacherValidator teacherValidator;
    private final TeacherSpecifications specifications;
    private String className;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              TeacherValidator teacherValidator,
                              DepartmentRepository departmentRepository,
                              TeacherSpecifications specifications,
                              TeachersJournalService journalService,
                              EventDataRepository dataRepository) {
        super(teacherRepository, specifications);
        this.teacherRepository = teacherRepository;
        this.teacherValidator = teacherValidator;
        this.departmentRepository = departmentRepository;
        this.specifications = specifications;
        this.journalService = journalService;
        this.dataRepository = dataRepository;
        this.className = Teacher.class.getSimpleName();
    }

    @Override
    protected Specification getSpec(FindAllData data) {
        return Specification.where(specifications.findTeachersByFacultyCriteria(data.getFacultyId()).and(specifications.getObjectByDeletedCriteria(data.getDeleted())));
    }

    @Override
    public void register(RegisterPersonData<Teacher> data) {
        checkExistenceObjectWithSuchIDBeforeRegistrationOrUpdate(new CheckExistsByIdData<>(className, getPersonUid(data), teacherRepository));
        validateObject(new ValidationData<>(teacherValidator, data.getNewPerson(), data.getBindingResult()));

        Teacher newTeacher = data.getNewPerson();

        setDepartmentForTeacher(newTeacher);

        savePerson(newTeacher);
        makeNewTeachersJournal(newTeacher, data);
    }

    private void setDepartmentForTeacher(Teacher teacher) {
        teacher.setDepartment(getDepartment(teacher));
    }

    private Department getDepartment(Teacher teacher) {
        return departmentRepository.findById(getDepartmentId(teacher)).get();
    }

    private Long getDepartmentId(Teacher teacher) {
        return teacher.getDepartment().getId();
    }

    private void makeNewTeachersJournal(Teacher teacher, RegisterPersonData data) {
        journalService.register(new RegisterTeachersJournalData(teacher, data.getBindingResult()));
    }

    @Override
    public void update(UpdatePersonData<Teacher> data) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, data.getUid(), teacherRepository));
        validateObject(new ValidationData<>(teacherValidator, data.getUpdatedPerson(), data.getBindingResult()));

        Teacher updatedTeacher = data.getUpdatedPerson();

        setIdInUpdatedPerson(updatedTeacher, data);
        setDepartmentForTeacher(updatedTeacher);

        savePerson(updatedTeacher);
    }

    @Override
    public void delete(Long uid) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, uid, teacherRepository));

        removeTeacherFromStudentGroups(getTeacher(uid));
        deleteTeachersJournal(uid);

        teacherRepository.deleteByUid(uid);
    }

    private Teacher getTeacher(Long teacherUid) {
        return teacherRepository.getByUid(teacherUid).get();
    }

    private void removeTeacherFromStudentGroups(Teacher teacher) {
        teacher.getStudentGroups().stream().forEach(studentGroup -> studentGroup.setCurator(null));
    }

    private void deleteTeachersJournal(Long teacherUid) {
        journalService.delete(journalService.findOneByTeacher(teacherUid).getId());
    }

    @Override
    public void softDelete(Long uid) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, uid, teacherRepository));

        softDeleteTeachersJournal(uid);

        savePerson(markPersonAsDeleted(findByUid(uid)));
    }

    private void softDeleteTeachersJournal(Long teacherUid) {
        journalService.softDelete(getTeachersJournalId(teacherUid));
    }

    private Long getTeachersJournalId(Long teacherUid) {
        return journalService.findOneByTeacher(teacherUid).getId();
    }

    @Override
    public void softDeletePeople(List<Teacher> people) {
        for (Teacher teacher : people)
            softDelete(teacher.getUid());
    }
}

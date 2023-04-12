package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.repositories.TeachersJournalRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.teachersJournal.RegisterTeachersJournalData;
import ua.dgma.electronicDeansOffice.services.interfaces.DepartmentService;
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
                              DepartmentRepository departmentRepository,
                              StudentGroupRepository groupRepository,
                              TeacherSpecifications specifications,
                              TeachersJournalService journalService) {
        super(teacherRepository, specifications);
        this.teacherRepository = teacherRepository;
        this.journalRepository = journalRepository;
        this.teacherValidator = teacherValidator;
        this.departmentRepository = departmentRepository;
        this.groupRepository = groupRepository;
        this.specifications = specifications;
        this.journalService = journalService;
        this.className = Teacher.class.getSimpleName();
    }

//    @Override
//    public List<Teacher> findAllWithPaginationOrWithoutByFaculty(FindAllData data) {
//        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
//            return teacherRepository.findAll(Specification.where(specifications.findTeachersByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))), Sort.by("surname"));
//        else
//            return teacherRepository.findAll(Specification.where(specifications.findTeachersByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))), PageRequest.of(data.getPage(), data.getObjectsPerPage(), Sort.by("surname"))).getContent();
//    }

    @Override
    protected Specification getSpec(FindAllData data) {
        return Specification.where(specifications.findTeachersByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted())));
    }

    @Override
    public void registerNew(RegisterPersonData<Teacher> data) {
        checkExistenceByIDBeforeRegistration(new CheckExistsByIdData<>(className, getPersonUid(data), teacherRepository));
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
        return departmentRepository.getByName(getDepartmentName(teacher)).get();
    }
    private String getDepartmentName(Teacher teacher) {
        return teacher.getDepartment().getName();
    }
    private void makeNewTeachersJournal(Teacher teacher, RegisterPersonData data) {
        journalService.registerNew(new RegisterTeachersJournalData(teacher, data.getBindingResult()));
    }

    @Override
    public void update(UpdatePersonData<Teacher> data) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, data.getUid(), teacherRepository));
        validateObject(new ValidationData<>(teacherValidator, data.getUpdatedPerson(), data.getBindingResult()));

        Teacher updatedTeacher = data.getUpdatedPerson();

        SetIdInUpdatedPerson(updatedTeacher, data);
        setDepartmentForTeacher(updatedTeacher);

        savePerson(updatedTeacher);
    }

    @Override
    public void delete(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, uid, teacherRepository));

        removeTeacherFromStudentGroups(getTeacher(uid));

        teacherRepository.deleteByUid(uid);
    }
    private Teacher getTeacher(Long teacherUid) {
        return teacherRepository.getByUid(teacherUid).get();
    }
    private void removeTeacherFromStudentGroups(Teacher teacher) {
        teacher.getStudentGroups().stream().forEach(studentGroup -> studentGroup.setCurator(null));
    }

    @Override
    public void softDelete(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, uid, teacherRepository));

        journalService.softDelete(getTeachersJournalId(uid));

        savePerson(markPersonAsDeleted(findByUid(uid)));
    }
    private Long getTeachersJournalId(Long teacherUid) {
        return journalRepository.getByTeacher_Uid(teacherUid).get().getId();
    }

    @Override
    public void softDeletePeople(List<Teacher> people) {
        for (Teacher teacher : people)
            softDelete(teacher.getUid());
    }
}

package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.repositories.TeachersJournalRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;
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
                              TeacherSpecifications specifications,
                              TeachersJournalService journalService) {
        super(teacherRepository, teacherValidator, exceptionData, specifications);
        this.teacherRepository = teacherRepository;
        this.journalRepository = journalRepository;
        this.teacherValidator = teacherValidator;
        this.departmentRepository = departmentRepository;
        this.specifications = specifications;
        this.journalService = journalService;
        this.className = Teacher.class.getSimpleName();
    }

    @Override
    public List<Teacher> findAllWithPaginationOrWithoutByFaculty(FindAllData data) {
        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return teacherRepository.findAll(Specification.where(specifications.findTeachersByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))));
        else
            return teacherRepository.findAll(Specification.where(specifications.findTeachersByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))), PageRequest.of(data.getPage(), data.getObjectsPerPage())).getContent();
    }

    @Override
    public void registerNew(RegisterPersonData<Teacher> data) {
        checkExistenceByIDBeforeRegistration(new CheckExistsByIdData<>(className, data.getNewPerson().getUid().longValue(), teacherRepository));
//        validateObject(new ValidationData<>(teacherValidator, teacher, bindingResult));
        validateObject(new ValidationData<>(teacherValidator, data.getNewPerson(), data.getBindingResult()));

        Teacher newTeacher = data.getNewPerson();
        newTeacher.setDepartment(departmentRepository.getByName(newTeacher.getDepartment().getName()).get());

        teacherRepository.save(newTeacher);
        journalService.registerNew(newTeacher, data.getBindingResult());
    }

    @Override
    public void updateByUid(UpdatePersonData<Teacher> data) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, data.getUid(), teacherRepository));
        validateObject(new ValidationData<>(teacherValidator, data.getUpdatedPerson(), data.getBindingResult()));

        Teacher updatedTeacher = data.getUpdatedPerson();
        updatedTeacher.setUid(data.getUid());
        updatedTeacher.setDepartment(departmentRepository.getByName(updatedTeacher.getDepartment().getName()).get());

        teacherRepository.save(updatedTeacher);
    }

    @Override
    public void deleteByUId(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, uid, teacherRepository));

        deleteTeacherFromJournal(journalRepository.getByTeacher_Uid(uid).get());

        teacherRepository.deleteByUid(uid);
    }

    @Override
    public void softDeleteByUId(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, uid, teacherRepository));

        Teacher teacher = findByUid(uid);
        teacher.setDeleted(true);
        journalService.softDeleteById(journalRepository.getByTeacher_Uid(uid).get().getId());

        teacherRepository.save(teacher);
    }

    private void deleteTeacherFromJournal(TeachersJournal journal) {
        journal.setTeacher(null);
        journalRepository.save(journal);
    }
}

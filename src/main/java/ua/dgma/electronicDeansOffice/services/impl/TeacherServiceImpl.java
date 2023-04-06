package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.repositories.TeachersJournalRepository;
import ua.dgma.electronicDeansOffice.services.specifications.Specifications;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.TeacherValidator;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.*;

@Service
@Transactional(readOnly = true)
public class TeacherServiceImpl extends PeopleServiceImpl<Teacher>{

    private final TeacherRepository teacherRepository;
    private final TeachersJournalRepository teachersJournalRepository;
    private final DepartmentRepository departmentRepository;
    private final TeacherValidator teacherValidator;
    private final Specifications<Teacher> specifications;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              TeacherValidator teacherValidator,
                              ExceptionData exceptionData,
                              TeachersJournalRepository teachersJournalRepository,
                              DepartmentRepository departmentRepository,
                              Specifications<Teacher> specifications) {
        super(teacherRepository, teacherValidator, exceptionData, specifications);
        this.teacherRepository = teacherRepository;
        this.teacherValidator = teacherValidator;
        this.teachersJournalRepository = teachersJournalRepository;
        this.departmentRepository = departmentRepository;
        this.specifications = specifications;
    }

    @Override
    public void registerNew(Teacher teacher, BindingResult bindingResult) {
        checkExistenceByIDBeforeRegistration(new CheckExistsByIdData<>(Teacher.class.getSimpleName(), teacher.getUid().longValue(), teacherRepository));
        validateObject(new ValidationData<>(teacherValidator, teacher, bindingResult));

        teacher.setDepartment(departmentRepository.getByName(teacher.getDepartment().getName()).get());

        teacherRepository.save(teacher);
    }

    @Override
    public void updateByUid(Long uid, Teacher updatedTeacher, BindingResult bindingResult) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(Teacher.class.getSimpleName(), uid, teacherRepository));
        validateObject(new ValidationData<>(teacherValidator, updatedTeacher, bindingResult));

        updatedTeacher.setUid(uid);
        updatedTeacher.setDepartment(departmentRepository.getByName(updatedTeacher.getDepartment().getName()).get());

        teacherRepository.save(updatedTeacher);
    }

    @Override
    public void deleteByUId(Long uid) {
        teacherRepository.deleteByUid(uid);
    }
}

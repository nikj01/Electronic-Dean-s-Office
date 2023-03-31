package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.services.specifications.Specifications;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.TeacherValidator;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;

@Service
@Transactional(readOnly = true)
public class TeacherServiceImpl extends PeopleServiceImpl<Teacher>{

    private final TeacherRepository teacherRepository;
    private final TeacherValidator teacherValidator;
    private final Specifications<Teacher> specifications;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              TeacherValidator teacherValidator,
                              ExceptionData exceptionData,
                              Specifications<Teacher> specifications) {
        super(teacherRepository, teacherValidator, exceptionData, specifications);
        this.teacherRepository = teacherRepository;
        this.teacherValidator = teacherValidator;
        this.specifications = specifications;
    }

    @Override
    public void registerNew(Teacher teacher, BindingResult bindingResult) {
        validateObject(new ValidationData<>(teacherValidator, teacher, bindingResult));

//        student.setStudentGroup(studentGroupService.findByName(student.getStudentGroup().getName()));

        teacherRepository.save(teacher);
    }

    @Override
    public void updateByUid(Long uid, Teacher updatedTeacher, BindingResult bindingResult) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(Teacher.class.getSimpleName(), uid, teacherRepository));
        validateObject(new ValidationData<>(teacherValidator, updatedTeacher, bindingResult));

        updatedTeacher.setUid(uid);
        teacherRepository.save(updatedTeacher);
    }

    @Override
    public void deleteByUId(Long uid) {
        teacherRepository.deleteByUid(uid);
    }
}

package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.repositories.TeachersJournalRepository;
import ua.dgma.electronicDeansOffice.services.specifications.TeacherSpecifications;
import ua.dgma.electronicDeansOffice.services.specifications.impl.SpecificationsImpl;
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
    private final DepartmentRepository departmentRepository;
    private final TeacherValidator teacherValidator;
    private final TeacherSpecifications specifications;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              TeacherValidator teacherValidator,
                              ExceptionData exceptionData,
                              DepartmentRepository departmentRepository,
                              TeacherSpecifications specifications) {
        super(teacherRepository, teacherValidator, exceptionData, specifications);
        this.teacherRepository = teacherRepository;
        this.teacherValidator = teacherValidator;
        this.departmentRepository = departmentRepository;
        this.specifications = specifications;
    }

    @Override
    public List<Teacher> findAllWithPaginationOrWithoutByFaculty(Integer page, Integer peoplePerPage, Boolean isDeleted, String facultyName) {
        if(checkPaginationParameters(page, peoplePerPage))
            return teacherRepository.findAll(Specification.where(specifications.findTeachersByFacultyCriteria(facultyName).and(specifications.getObjectByDeletedCriteria(isDeleted))));
        else
            return teacherRepository.findAll(Specification.where(specifications.findTeachersByFacultyCriteria(facultyName).and(specifications.getObjectByDeletedCriteria(isDeleted))), PageRequest.of(page, peoplePerPage)).getContent();
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

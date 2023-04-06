package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;
import ua.dgma.electronicDeansOffice.services.specifications.StudentSpecifications;
import ua.dgma.electronicDeansOffice.services.specifications.impl.SpecificationsImpl;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.StudentValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.*;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl extends PeopleServiceImpl<Student> {

    private final StudentRepository studentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final StudentValidator studentValidator;
    private final StudentSpecifications specifications;

    @Autowired
    protected StudentServiceImpl(StudentRepository studentRepository,
                                 ExceptionData exceptionData,
                                 StudentValidator studentValidator,
                                 StudentGroupRepository studentGroupRepository,
                                 StudentSpecifications specifications) {
        super(studentRepository, studentValidator, exceptionData, specifications);
        this.studentRepository = studentRepository;
        this.studentValidator = studentValidator;
        this.studentGroupRepository = studentGroupRepository;
        this.specifications = specifications;
    }

    @Override
    public List<Student> findAllWithPaginationOrWithoutByFaculty(Integer page, Integer peoplePerPage, Boolean isDeleted, String facultyName) {
        if(checkPaginationParameters(page, peoplePerPage))
            return studentRepository.findAll(Specification.where(specifications.findStudentsByFacultyCriteria(facultyName).and(specifications.getObjectByDeletedCriteria(isDeleted))));
        else
            return studentRepository.findAll(Specification.where(specifications.findStudentsByFacultyCriteria(facultyName).and(specifications.getObjectByDeletedCriteria(isDeleted))), PageRequest.of(page, peoplePerPage)).getContent();
    }

    @Override
    public void registerNew(Student student, BindingResult bindingResult) {
        checkExistenceByIDBeforeRegistration(new CheckExistsByIdData<>(Student.class.getSimpleName(), student.getUid(), studentRepository));
        validateObject(new ValidationData<>(studentValidator, student, bindingResult));

        student.setStudentGroup(studentGroupRepository.getByName(student.getStudentGroup().getName()).get());

        studentRepository.save(student);
    }

    @Override
    public void updateByUid(Long uid, Student updatedStudent, BindingResult bindingResult) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(Student.class.getSimpleName(), uid, studentRepository));
        validateObject(new ValidationData<>(studentValidator, updatedStudent, bindingResult));

        updatedStudent.setUid(uid);
        updatedStudent.setStudentGroup(studentGroupRepository.getByName(updatedStudent.getStudentGroup().getName()).get());

        studentRepository.save(updatedStudent);
    }

    @Override
    public void deleteByUId(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(Student.class.getSimpleName(), uid, studentRepository));

        if(studentGroupRepository.getByGroupLeader_Uid(Long.valueOf(uid)).isPresent())
            studentGroupRepository.getByGroupLeader_Uid(uid).stream().findFirst().ifPresent(studentGroup -> studentGroup.setGroupLeader(null));

        studentRepository.deleteByUid(uid);
    }

}

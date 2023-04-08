package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;
import ua.dgma.electronicDeansOffice.services.specifications.StudentSpecifications;
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
    private String className;

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
        this.className = Student.class.getSimpleName();
    }

    @Override
    public List<Student> findAllWithPaginationOrWithoutByFaculty(FindAllData data) {
        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return studentRepository.findAll(Specification.where(specifications.findStudentsByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))), Sort.by("surname"));
        else
            return studentRepository.findAll(Specification.where(specifications.findStudentsByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))), PageRequest.of(data.getPage(), data.getObjectsPerPage(), Sort.by("surname"))).getContent();
    }

    @Override
    public void registerNew(RegisterPersonData<Student> data) {
        checkExistenceByIDBeforeRegistration(new CheckExistsByIdData<>(className, data.getNewPerson().getUid(), studentRepository));
        validateObject(new ValidationData<>(studentValidator, data.getNewPerson(), data.getBindingResult()));

        Student newStudent = data.getNewPerson();
        newStudent.setStudentGroup(studentGroupRepository.getByName(newStudent.getStudentGroup().getName()).get());

        studentRepository.save(newStudent);
    }

    @Override
    public void updateByUid(UpdatePersonData<Student> data) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, data.getUid(), studentRepository));
        validateObject(new ValidationData<>(studentValidator, data.getUpdatedPerson(), data.getBindingResult()));

        Student updatedStudent = data.getUpdatedPerson();
        updatedStudent.setUid(data.getUid());
        updatedStudent.setStudentGroup(studentGroupRepository.getByName(updatedStudent.getStudentGroup().getName()).get());

        studentRepository.save(updatedStudent);
    }

    @Override
    public void deleteByUId(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, uid, studentRepository));

        if(studentGroupRepository.getByGroupLeader_Uid(Long.valueOf(uid)).isPresent())
            studentGroupRepository.getByGroupLeader_Uid(uid).stream().findFirst().ifPresent(studentGroup -> studentGroup.setGroupLeader(null));

        studentRepository.deleteByUid(uid);
    }

    @Override
    public void softDeleteByUId(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, uid, studentRepository));

        Student student = findByUid(uid);
        student.setDeleted(true);

        studentRepository.save(student);
    }
}

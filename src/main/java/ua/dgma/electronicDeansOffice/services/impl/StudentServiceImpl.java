package ua.dgma.electronicDeansOffice.services.impl;

import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.ExceptionData;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.models.Student;
//import ua.dgma.electronicDeansOffice.models.QStudent;
//import ua.dgma.electronicDeansOffice.models.QPerson;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;
import ua.dgma.electronicDeansOffice.services.specifications.Specifications;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.StudentValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkPaginationParameters;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl extends PeopleServiceImpl<Student> {

    private final StudentRepository studentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final StudentValidator studentValidator;
    private final Specifications<Student> specifications;

    @Autowired
    protected StudentServiceImpl(StudentRepository studentRepository,
                                 ExceptionData exceptionData,
                                 StudentValidator studentValidator,
                                 Specifications<Student> specifications,
                                 StudentGroupRepository studentGroupRepository) {
        super(studentRepository, studentValidator, exceptionData, specifications);
        this.studentRepository = studentRepository;
        this.studentValidator = studentValidator;
        this.studentGroupRepository = studentGroupRepository;
        this.specifications = specifications;
    }

    @Override
    public void updateByUid(Long uid, Student updatedStudent, BindingResult bindingResult) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(Student.class.getSimpleName(), uid, studentRepository));
        validateObject(new ValidationData<>(studentValidator, updatedStudent, bindingResult));

        updatedStudent.setUid(uid);

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

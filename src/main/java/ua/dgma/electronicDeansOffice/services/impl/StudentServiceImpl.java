package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.exceptions.ExceptionData;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.StudentValidator;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl extends PeopleServiceImpl<Student> {

    private final StudentRepository studentRepository;
    private final StudentValidator studentValidator;

    @Autowired
    protected StudentServiceImpl(StudentRepository studentRepository,
                                 ExceptionData exceptionData,
                                 StudentValidator studentValidator) {
        super(studentRepository, studentValidator, exceptionData);
        this.studentRepository = studentRepository;
        this.studentValidator = studentValidator;
    }

    @Override
    public void updateByUid(Long uid, Student updatedStudent, BindingResult bindingResult) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(Student.class.getSimpleName(), uid, studentRepository));
        validateObject(new ValidationData<>(studentValidator, updatedStudent, bindingResult));

        updatedStudent.setUid(uid);

        studentRepository.save(updatedStudent);
    }


    /*
*   public void deleteByUid(Long uid)
*   student group.get.deleteThisStudent
* */
}

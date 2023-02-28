package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.people.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;
import ua.dgma.electronicDeansOffice.utill.validators.StudentValidator;



@Service
@Transactional(readOnly = true)
public class StudentServiceImpl extends PeopleServiceImpl<Student> {

    private final StudentRepository studentRepository;

    private final ExceptionData exceptionData;

    @Autowired
    protected StudentServiceImpl(StudentRepository studentRepository,
                                 StudentValidator validator,
                                 ExceptionData exceptionData) {
        super(studentRepository, validator, exceptionData);
        this.studentRepository = studentRepository;
        this.exceptionData = exceptionData;
    }

    @Override
    public void updateByUid(Long uid, Student updatedStudent, BindingResult bindingResult) {
        checkExistsWithSuchUid(uid);
        validatePerson(updatedStudent, bindingResult);

        updatedStudent.setUid(uid);
        studentRepository.save(updatedStudent);
    }


    /*
*   public void deleteByUid(Long uid)
*   student group.get.deleteThisStudent
* */
}

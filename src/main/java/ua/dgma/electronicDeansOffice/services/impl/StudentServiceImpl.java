package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;
import ua.dgma.electronicDeansOffice.utill.validators.StudentValidator;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class StudentServiceImpl extends PeopleServiceImpl<Student> {

    private final StudentRepository studentRepository;


    @Autowired
    protected StudentServiceImpl(StudentValidator validator,
                                 StudentRepository studentRepository) {
        super(studentRepository, validator);
        this.studentRepository = studentRepository;
    }

    @Override
    public void registerNew(Student student, BindingResult bindingResult) {
        validatePerson(student, bindingResult);
        studentRepository.save(student);
    }

    @Override
    public void updateByUid(Long uid, Student updatedStudent, BindingResult bindingResult) {
        checkExistsWithSuchUid(uid);
        validatePerson(updatedStudent, bindingResult);

        updatedStudent.setUid(uid);
        studentRepository.save(updatedStudent);
    }


//    public List<Student> findAllStudents(){
//        return studentRepository.findAll();
//    }


    /*
*   public void deleteByUid(Long uid)
*   student group.get.deleteThisStudent
*
* */
}

package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl extends PeopleServiceImpl<Student>{

    private final PeopleRepository<Student> studentRepository;

    protected StudentServiceImpl(PeopleRepository<Student> repository,
                                 Validator validator,
                                 PeopleRepository<Student> studentRepository) {
        super((PeopleRepository<Student>) repository, validator);
        this.studentRepository = studentRepository;
    }

//    protected StudentServiceImpl(PeopleRepository<Student> repository,
//                                 PeopleValidator validator,
//                                 PeopleRepository<Student> studentRepository) {
//        super(repository, validator);
////        this.studentRepository = studentRepository;
//        this.studentRepository = studentRepository;
//    }


    @Override
    public void updateByUid(Long uid, Student uodatedStudent, BindingResult bindingResult) {
        checkExistsWithSuchUid(uid);
        validate(uodatedStudent, bindingResult);

        uodatedStudent.setUid(uid);
        studentRepository.save(uodatedStudent);
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

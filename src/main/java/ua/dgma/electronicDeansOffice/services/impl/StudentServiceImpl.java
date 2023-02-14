package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements PeopleService<Student> {

    private final PeopleRepository<Student> repository;

    @Autowired
    public StudentServiceImpl(PeopleRepository repository) {
        this.repository = repository;
    }

//    @Override
//    public Student findStudentByUid(Long uid) {
//        Optional<Student> foundStudent = repository.getByUid(uid);
//        return foundStudent.orElse(null);
//    }

    @Override
    public Student findOneByUid(Long uid) {
        Optional<Student> foundStudent = repository.getByUid(uid);
        return foundStudent.orElse(null);
    }
}

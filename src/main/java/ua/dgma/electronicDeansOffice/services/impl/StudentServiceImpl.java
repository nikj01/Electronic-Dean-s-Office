package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Student.StudentDTO;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements PeopleService<Student, StudentDTO> {

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
    public StudentDTO findOneByUid(Long uid) {
//        Optional<Student> foundStudent = repository.getByUid(uid);
//        return foundStudent.orElse(null);
        return null;
    }

    @Override
    public StudentDTO findOneByEmail(String email) {
        return null;
    }

    @Override
    public StudentDTO findOneBySurname(String surname) {
        return null;
    }

    @Override
    public List<StudentDTO> findAll() {
        return null;
    }

    @Override
    public void registerNewPerson(StudentDTO person) {

    }

}

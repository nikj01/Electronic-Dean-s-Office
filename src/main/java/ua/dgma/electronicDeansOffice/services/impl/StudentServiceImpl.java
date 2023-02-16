package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Student.StudentGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Student.StudentsGetDTO;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl { //implements PeopleService<Student, StudentGetDTO, StudentsGetDTO> {
//
//    private final PeopleRepository<Student> repository;
//
//    @Autowired
//    public StudentServiceImpl(PeopleRepository repository) {
//        this.repository = repository;
//    }
//
////    @Override
////    public Student findStudentByUid(Long uid) {
////        Optional<Student> foundStudent = repository.getByUid(uid);
////        return foundStudent.orElse(null);
////    }
//
//    @Override
//    public StudentGetDTO findOneByUid(Long uid) {
////        Optional<Student> foundStudent = repository.getByUid(uid);
////        return foundStudent.orElse(null);
//        return null;
//    }
//
//    @Override
//    public StudentGetDTO findOneByEmail(String email) {
//        return null;
//    }
//
//    @Override
//    public StudentGetDTO findOneBySurname(String surname) {
//        return null;
//    }
//
//    @Override
//    public StudentsGetDTO findAll() {
//        return null;
//    }
//
//
//    @Override
//    public void registerNewPerson(StudentGetDTO person) {
//
//    }

}

package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl {

    private final StudentRepository repository;
    @Autowired
    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    public Student findOne(Long uid){
        Optional<Student> foundStudent = repository.findByUid(uid);
        return foundStudent.orElse(null);
    }

    public List<Student> findAllStudentsByGroup(String group){
        return repository.findStudentByGroupName(group);
    }

    @Transactional
    public void saveStudent(Student student){
        repository.save(student);
    }

    @Transactional
    public void updateStudent(Long uid, Student updatedStudent){
        Student studentToBeUpdated = repository.findByUid(uid).get();
        updatedStudent.setUid(uid);
        repository.save(updatedStudent);
    }

    @Transactional
    public void deleteStudent(Long uid){
        repository.deleteByUid(uid);
    }


}

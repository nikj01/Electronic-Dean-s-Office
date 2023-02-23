package ua.dgma.electronicDeansOffice.mapstruct.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.StudentMapper;
import ua.dgma.electronicDeansOffice.models.Student;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentsMapperImpl implements StudentMapper {

    private final ModelMapper mapper;

    @Autowired
    public StudentsMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Student convertToStudent(StudentPostDTO studentPost) {
        return mapper.map(studentPost, Student.class);
    }

    @Override
    public StudentGetDTO convertToStudentGetDTO(Student student) {
        return mapper.map(student, StudentGetDTO.class);
    }

    @Override
    public StudentSlimGetDTO convertToStudentSlimGetDTO(Student student) {
        return mapper.map(student, StudentSlimGetDTO.class);
    }

    @Override
    public StudentsGetDTO convertToStudentsGetDTO(List<Student> students) {
        return new StudentsGetDTO(students.stream()
                                          .map(this::convertToStudentGetDTO)
                                          .sorted()
                                          .collect(Collectors.toList()));
    }

    @Override
    public StudentsSlimGetDTO convertToStudentsSlimGetDTO(List<Student> students) {
        return new StudentsSlimGetDTO(students.stream()
                                              .map(this::convertToStudentSlimGetDTO)
                                              .sorted()
                                              .collect(Collectors.toList()));
    }
}

package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.*;
import ua.dgma.electronicDeansOffice.models.Student;

import java.util.List;

public interface StudentMapper {

    Student convertToStudent(StudentPostDTO studentPost);

    StudentGetDTO convertToStudentGetDTO(Student student);

    StudentSlimGetDTO convertToStudentSlimGetDTO(Student student);

    StudentsGetDTO convertToStudentsGetDTO(List<Student> students);

    StudentsSlimGetDTO convertToStudentsSlimGetDTO(List<Student> students);
}

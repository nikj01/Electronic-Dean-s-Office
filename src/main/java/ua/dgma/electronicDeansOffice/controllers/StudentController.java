package ua.dgma.electronicDeansOffice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dgma.electronicDeansOffice.dto.StudentDTO;
import ua.dgma.electronicDeansOffice.dto.StudentsResponse;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.services.impl.StudentServiceImpl;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentServiceImpl studentService;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentController(StudentServiceImpl studentService, ModelMapper modelMapper){
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public StudentsResponse getStudents(){
        return new StudentsResponse(
                studentService
                        .findAllStudents()
                        .stream()
                        .map(this::convertToStudentDTO)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{uid}")
    public Student getStudent(@PathVariable Long uid){
//        return new StudentDTO();
        return null;
    }

    private StudentDTO convertToStudentDTO(Student student){
        return modelMapper.map(student, StudentDTO.class);
    }

    private Student convertToStudent(StudentDTO studentDTO){
        return modelMapper.map(studentDTO, Student.class);
    }
}

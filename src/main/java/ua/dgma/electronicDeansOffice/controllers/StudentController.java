package ua.dgma.electronicDeansOffice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Student.StudentDTO;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.services.impl.StudentServiceImpl;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentServiceImpl studentService;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentController(StudentServiceImpl studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{uid}")
    public StudentDTO findStudentByUid(@PathVariable("uid") Long uid){
//        return convertToStudentDTO(studentService.findOneByUid(uid));
        return null;
    }

    private StudentDTO convertToStudentDTO(Student student){
        return modelMapper.map(student, StudentDTO.class);
    }

    private Person convertToStudent(StudentDTO studentDTO){
        return modelMapper.map(studentDTO, Student.class);
    }
}

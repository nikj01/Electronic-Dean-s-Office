package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.exceptions.CustomException;
import ua.dgma.electronicDeansOffice.exceptions.ErrorResponse;
import ua.dgma.electronicDeansOffice.exceptions.people.NotFoundException;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.StudentMapper;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.services.impl.StudentServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentsController {

    private final StudentServiceImpl studentService;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentsController(StudentServiceImpl studentService,
                              StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @GetMapping("/findByUid")
    @ResponseStatus(HttpStatus.FOUND)
    public StudentGetDTO findStudentByUid(@RequestParam(value = "uid") Long uid) {
        return studentMapper.convertToStudentGetDTO(studentService.findByUid(uid));
    }

    @GetMapping("/slim/findByUid")
    @ResponseStatus(HttpStatus.FOUND)
    public StudentSlimGetDTO findSlimStudentByUid(@RequestParam(value = "uid") Long uid) {
        return studentMapper.convertToStudentSlimGetDTO(studentService.findByUid(uid));
    }

    @GetMapping("/findByEmail")
    @ResponseStatus(HttpStatus.FOUND)
    public StudentGetDTO findStudentByEmail(@RequestParam(value = "email") String email) {
        return studentMapper.convertToStudentGetDTO(studentService.findByEmail(email));
    }

    @GetMapping("/slim/findByEmail")
    @ResponseStatus(HttpStatus.FOUND)
    public StudentSlimGetDTO findSlimStudentByEmail(@RequestParam(value = "email") String email) {
        return studentMapper.convertToStudentSlimGetDTO(studentService.findByEmail(email));
    }

    @GetMapping("/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public StudentsGetDTO findStudentBySurname(@RequestParam(value = "surname") String surname) {
        return studentMapper.convertToStudentsGetDTO(studentService.findBySurname(surname));
    }

    @GetMapping("/slim/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public StudentsSlimGetDTO findSlimStudentBySurname(@RequestParam(value = "surname") String surname) {
        return studentMapper.convertToStudentsSlimGetDTO(studentService.findBySurname(surname));
    }

    @GetMapping()
    public StudentsGetDTO findAllStudents(@RequestParam(value = "page", required = false) Integer page,
                                          @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
        return studentMapper.convertToStudentsGetDTO(studentService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @GetMapping("/slim")
    public StudentsSlimGetDTO findAllSlimStudents(@RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
        return studentMapper.convertToStudentsSlimGetDTO(studentService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewStudent(@RequestBody @Valid StudentPostDTO newPostStudent,
                                                       BindingResult bindingResult) {
        Student newStudent = studentMapper.convertToStudent(newPostStudent);

        studentService.registerNew(newStudent, bindingResult);
    }

    @PatchMapping("/update")
    public void updateStudent(@RequestParam("uid") Long uid,
                              @RequestBody @Valid  StudentPostDTO updatedPostStudent,
                                                   BindingResult bindingResult) {
        Student updatedStudent = studentMapper.convertToStudent(updatedPostStudent);

        studentService.updateByUid(uid, updatedStudent, bindingResult);
    }

    @DeleteMapping("/delete")
    public void deleteStudent(@RequestParam("uid") Long uid) {
        studentService.deleteByUId(uid);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(CustomException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}

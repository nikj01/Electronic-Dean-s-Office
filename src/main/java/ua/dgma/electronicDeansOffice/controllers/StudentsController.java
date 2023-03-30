package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.exceptions.CustomException;
import ua.dgma.electronicDeansOffice.exceptions.ErrorResponse;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.StudentListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.StudentMapper;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.services.impl.StudentServiceImpl;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsController {

    private final PeopleService<Student> studentService;
    private final StudentMapper studentMapper;
    private final StudentListMapper studentListMapper;

    @Autowired
    public StudentsController(StudentServiceImpl studentService,
                              StudentMapper studentMapper,
                              StudentListMapper studentListMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
        this.studentListMapper = studentListMapper;
    }

    @GetMapping("/findByUid")
    @ResponseStatus(HttpStatus.FOUND)
    public StudentGetDTO findStudentByUid(@RequestParam("uid") Long uid) {
        return studentMapper.toStudentGetDTO(studentService.findByUid(uid));
    }

    @GetMapping("/slim/findByUid")
    @ResponseStatus(HttpStatus.FOUND)
    public StudentSlimGetDTO findSlimStudentByUid(@RequestParam("uid") Long uid) {
        return studentMapper.toStudentSlimGetDTO(studentService.findByUid(uid));
    }

    @GetMapping("/findByEmail")
    @ResponseStatus(HttpStatus.FOUND)
    public StudentGetDTO findStudentByEmail(@RequestParam("email") String email) {
        return studentMapper.toStudentGetDTO(studentService.findByEmail(email));
    }

    @GetMapping("/slim/findByEmail")
    @ResponseStatus(HttpStatus.FOUND)
    public StudentSlimGetDTO findSlimStudentByEmail(@RequestParam("email") String email) {
        return studentMapper.toStudentSlimGetDTO(studentService.findByEmail(email));
    }

    @GetMapping("/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public List<StudentGetDTO> findStudentBySurname(@RequestParam("surname") String surname) {
        return studentListMapper.toStudentsGetDTO(studentService.findBySurname(surname));
    }

    @GetMapping("/slim/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public List<StudentSlimGetDTO> findSlimStudentBySurname(@RequestParam("surname") String surname) {
        return studentListMapper.toStudentsSlimGetDTO(studentService.findBySurname(surname));
    }

    @GetMapping()
    public List<StudentGetDTO> findAllStudents(@RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "peoplePerPage", required = false) Integer peoplePerPage,
                                               @RequestParam(value = "isDeleted", required = false, defaultValue = "false") Boolean isDeleted,
                                               @RequestParam(value = "faculty", required = false) String facultyName) {
        return studentListMapper.toStudentsGetDTO(studentService.findAllPeople(page, peoplePerPage, isDeleted, facultyName));
    }

    @GetMapping("/slim")
    public List<StudentSlimGetDTO> findAllSlimStudents(@RequestParam(value = "page", required = false) Integer page,
                                                       @RequestParam(value = "peoplePerPage", required = false) Integer peoplePerPage,
                                                       @RequestParam(value = "isDeleted", required = false, defaultValue = "false") Boolean isDeleted,
                                                       @RequestParam(value = "faculty", required = false) String facultyName) {
        return studentListMapper.toStudentsSlimGetDTO(studentService.findAllPeople(page, peoplePerPage, isDeleted, facultyName));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewStudent(@RequestBody @Valid StudentPostDTO newPostStudent,
                                                       BindingResult bindingResult) {
        Student newStudent = studentMapper.toStudent(newPostStudent);

        studentService.registerNew(newStudent, bindingResult);
    }

    @PatchMapping("/update")
    public void updateStudent(@RequestParam("uid") Long uid,
                              @RequestBody @Valid  StudentPostDTO updatedPostStudent,
                                                   BindingResult bindingResult) {
        Student updatedStudent = studentMapper.toStudent(updatedPostStudent);

        studentService.updateByUid(uid, updatedStudent, bindingResult);
    }

    @DeleteMapping("/delete")
    public void deleteStudent(@RequestParam("uid") Long uid) {
        studentService.deleteByUId(uid);
    }

    @DeleteMapping("/soft/delete")
    public void softDeletePerson(@RequestParam("uid") Long uid) {
        studentService.softDeleteByUId(uid);
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

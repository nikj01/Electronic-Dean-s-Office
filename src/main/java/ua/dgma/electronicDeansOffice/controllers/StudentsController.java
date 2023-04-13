package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.StudentListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.StudentMapper;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.services.impl.StudentServiceImpl;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;
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
    public List<StudentSlimGetDTO> findSlimStudentByEmail(@RequestParam("email") String email) {
        return studentListMapper.toStudentsSlimGetDTO(studentService.findByEmail(email));
    }

    @GetMapping("/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public List<StudentSlimGetDTO> findSlimStudentBySurname(@RequestParam("surname") String surname) {
        return studentListMapper.toStudentsSlimGetDTO(studentService.findBySurname(surname));
    }

    @GetMapping()
    public List<StudentSlimGetDTO> findAllSlimStudents(@RequestParam(value = "page", required = false) Integer page,
                                                       @RequestParam(value = "peoplePerPage", required = false) Integer peoplePerPage,
                                                       @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                       @RequestParam(value = "faculty", required = false) Long facultyId) {
        return studentListMapper.toStudentsSlimGetDTO(studentService.findAllPeople(new FindAllData(page, peoplePerPage, deleted, facultyId)));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewStudent(@RequestBody @Valid StudentPostDTO newPostStudent,
                                                       BindingResult bindingResult) {
        Student newStudent = studentMapper.toStudent(newPostStudent);

        studentService.register(new RegisterPersonData<>(newStudent, bindingResult));
    }

    @PatchMapping("/update")
    public void updateStudent(@RequestParam("uid") Long uid,
                              @RequestBody @Valid  StudentPatchDTO studentPatchDTO,
                                                   BindingResult bindingResult) {
        Student updatedStudent = studentMapper.toStudent(studentPatchDTO);

        studentService.update(new UpdatePersonData<>(uid, updatedStudent, bindingResult));
    }

    @DeleteMapping("/delete")
    public void deleteStudent(@RequestParam("uid") Long uid) {
        studentService.delete(uid);
    }

    @DeleteMapping("/soft/delete")
    public void softDeletePerson(@RequestParam("uid") Long uid) {
        studentService.softDelete(uid);
    }

}

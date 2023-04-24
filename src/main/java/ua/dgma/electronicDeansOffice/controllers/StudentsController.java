package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.StudentListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.StudentMapper;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.student.DataForStudentStatistics;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.interfaces.ReportsAnalyzer;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ua.dgma.electronicDeansOffice.utill.ConvertData.convertData;

@RestController
@RequestMapping("/students")
public class StudentsController {
    private final PeopleService<Student> studentService;
    private final ReportsAnalyzer reportsAnalyzer;
    private final StudentMapper studentMapper;
    private final StudentListMapper studentListMapper;

    @Autowired
    public StudentsController(PeopleService<Student> studentService,
                              ReportsAnalyzer reportsAnalyzer,
                              StudentMapper studentMapper,
                              StudentListMapper studentListMapper) {
        this.studentService = studentService;
        this.reportsAnalyzer = reportsAnalyzer;
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

    @GetMapping("/attendance")
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, Double> showStudentAvgAttendance(@RequestParam("uid") Long uid,
                                                      @RequestParam(value = "semester", required = false) Integer semester,
                                                      @RequestParam(value = "from", required = false) String searchFrom,
                                                      @RequestParam(value = "to", required = false) String searchTo) {
        return reportsAnalyzer.getAvgAttendanceForStudent(new DataForStudentStatistics(uid, semester, convertData(searchFrom), convertData(searchTo)));
    }

    @GetMapping("/avgGrade")
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, Double> showStudentAvgGrade(@RequestParam("uid") Long uid,
                                                 @RequestParam(value = "semester", required = false) Integer semester,
                                                 @RequestParam(value = "from", required = false) String searchFrom,
                                                 @RequestParam(value = "to", required = false) String searchTo) {
        return reportsAnalyzer.getAvgGradeForStudent(new DataForStudentStatistics(uid, semester, convertData(searchFrom), convertData(searchTo)));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.FOUND)
    public List<StudentSlimGetDTO> findAllSlimStudents(@RequestParam(value = "page", required = false) Integer page,
                                                       @RequestParam(value = "peoplePerPage", required = false) Integer peoplePerPage,
                                                       @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                       @RequestParam(value = "faculty", required = false) Long facultyId) {
        return studentListMapper.toStudentsSlimGetDTO(studentService.findAllPeople(new FindAllData(page, peoplePerPage, deleted, facultyId)));
    }

    @GetMapping("/facultyAttendance")
    @ResponseStatus(HttpStatus.FOUND)
    public Map<Long, Double> showStudentsAvgAttendanceOnFaculty(@RequestParam(value = "page", required = false) Integer page,
                                                                @RequestParam(value = "peoplePerPage", required = false) Integer peoplePerPage,
                                                                @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                                @RequestParam(value = "faculty", required = false) Long facultyId,
                                                                @RequestParam(value = "semester", required = false) Integer semester,
                                                                @RequestParam(value = "from", required = false) String searchFrom,
                                                                @RequestParam(value = "to", required = false) String searchTo) {

        return reportsAnalyzer.getAvgAttendanceForStudentsOnFaculty(new FindAllData(page, peoplePerPage, deleted, facultyId, semester, convertData(searchFrom), convertData(searchTo)));
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

package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.extractWithGrades.Extract;
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
import ua.dgma.electronicDeansOffice.services.interfaces.ReportsAnalyzerForStudent;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static ua.dgma.electronicDeansOffice.utill.ConvertData.convertData;

@RestController
@RequestMapping("/students")
public class StudentsController {
    private final PeopleService<Student> studentService;
    private final ReportsAnalyzerForStudent reportsAnalyzer;
    private final StudentMapper studentMapper;
    private final StudentListMapper studentListMapper;

    @Autowired
    public StudentsController(PeopleService<Student> studentService,
                              ReportsAnalyzerForStudent reportsAnalyzer,
                              StudentMapper studentMapper,
                              StudentListMapper studentListMapper) {
        this.studentService = studentService;
        this.reportsAnalyzer = reportsAnalyzer;
        this.studentMapper = studentMapper;
        this.studentListMapper = studentListMapper;
    }

    @GetMapping("/{uid}")
    @ResponseStatus(HttpStatus.FOUND)
    public StudentGetDTO findStudentByUid(@PathVariable("uid") Long uid) {
        return studentMapper.toStudentGetDTO(studentService.findByUid(uid));
    }

    @GetMapping("/emails/{email}")
    @ResponseStatus(HttpStatus.FOUND)
    public List<StudentSlimGetDTO> findSlimStudentByEmail(@PathVariable("email") String email) {
        return studentListMapper.toStudentsSlimGetDTO(studentService.findByEmail(email));
    }

    @GetMapping("surnames/{surname}")
    @ResponseStatus(HttpStatus.FOUND)
    public List<StudentSlimGetDTO> findSlimStudentBySurname(@PathVariable("surname") String surname) {
        return studentListMapper.toStudentsSlimGetDTO(studentService.findBySurname(surname));
    }

    @GetMapping("{uid}/attendance")
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, Double> showStudentAvgAttendance(@PathVariable("uid") Long uid,
                                                      @RequestParam(value = "from", required = false) String searchFrom,
                                                      @RequestParam(value = "to", required = false) String searchTo) {
        return reportsAnalyzer.getAvgAttendanceForStudent(new DataForStudentStatistics(uid, convertData(searchFrom), convertData(searchTo)));
    }

    @GetMapping("{uid}/avgGrade")
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, Double> showStudentAvgGrade(@PathVariable("uid") Long uid,
                                                 @RequestParam(value = "from", required = false) String searchFrom,
                                                 @RequestParam(value = "to", required = false) String searchTo) {
        return reportsAnalyzer.getAvgGradeForStudent(new DataForStudentStatistics(uid, convertData(searchFrom), convertData(searchTo)));
    }

    @GetMapping("{uid}/extractRatings")
    @ResponseStatus(HttpStatus.OK)
    public List<Extract> showStudentExtractWithRatings(@PathVariable("uid") Long uid) {
        return reportsAnalyzer.getExtractWithGradesForStudent(new DataForStudentStatistics(uid));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.FOUND)
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

    @PatchMapping("{uid}/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateStudent(@PathVariable("uid") Long uid,
                              @RequestBody @Valid  StudentPatchDTO studentPatchDTO,
                                                   BindingResult bindingResult) {
        Student updatedStudent = studentMapper.toStudent(studentPatchDTO);

        studentService.update(new UpdatePersonData<>(uid, updatedStudent, bindingResult));
    }

    @DeleteMapping("{uid}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable("uid") Long uid) {
        studentService.delete(uid);
    }

    @DeleteMapping("{uid}/softDelete")
    @ResponseStatus(HttpStatus.OK)
    public void softDeletePerson(@PathVariable("uid") Long uid) {
        studentService.softDelete(uid);
    }

}

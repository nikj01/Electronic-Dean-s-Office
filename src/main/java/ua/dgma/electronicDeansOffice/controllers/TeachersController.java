package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.TeacherListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.TeacherMapper;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeachersController {
    private final PeopleService<Teacher> teacherService;
    private final TeacherMapper teacherMapper;
    private final TeacherListMapper teacherListMapper;

    @Autowired
    public TeachersController(PeopleService<Teacher> teacherService,
                              TeacherMapper teacherMapper,
                              TeacherListMapper teacherListMapper) {
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
        this.teacherListMapper = teacherListMapper;
    }

    @GetMapping("/findByUid")
    @ResponseStatus(HttpStatus.FOUND)
    public TeacherGetDTO findTeacherByUid(@RequestParam("uid") Long uid) {
        return teacherMapper.toTeacherGetDTO(teacherService.findByUid(uid));
    }

    @GetMapping("/slim/findByUid")
    @ResponseStatus(HttpStatus.FOUND)
    public TeacherSlimGetDTO findSlimTeacherByUid(@RequestParam("uid") Long uid) {
        return teacherMapper.toTeacherSlimGetDTO(teacherService.findByUid(uid));
    }

    @GetMapping("/findByEmail")
    @ResponseStatus(HttpStatus.FOUND)
    public List<TeacherSlimGetDTO> findSlimTeacherByEmail(@RequestParam("email") String email) {
        return teacherListMapper.toTeachersSlimGetDTO(teacherService.findByEmail(email));
    }

    @GetMapping("/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public List<TeacherSlimGetDTO> findSlimTeacherBySurname(@RequestParam("surname") String surname) {
        return teacherListMapper.toTeachersSlimGetDTO(teacherService.findBySurname(surname));
    }

    @GetMapping()
    public List<TeacherSlimGetDTO> findAllSlimTeachers(@RequestParam(value = "page", required = false) Integer page,
                                                       @RequestParam(value = "peoplePerPage", required = false) Integer peoplePerPage,
                                                       @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean isDeleted,
                                                       @RequestParam(value = "faculty", required = false) String facultyName) {
        return teacherListMapper.toTeachersSlimGetDTO(teacherService.findAllPeople(new FindAllData(page, peoplePerPage, isDeleted, facultyName)));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewTeacher(@RequestBody @Valid TeacherPostDTO teacherPostDTO,
                                                       BindingResult bindingResult) {
        Teacher newTeacher = teacherMapper.toTeacher(teacherPostDTO);

        teacherService.registerNew(new RegisterPersonData<>(newTeacher, bindingResult));
    }

    @PatchMapping("/update")
    public void updateTeacher(@RequestParam("uid") Long uid,
                              @RequestBody @Valid  TeacherPatchDTO teacherPatchDTO,
                                                   BindingResult bindingResult) {
        Teacher newTeacher = teacherMapper.toTeacher(teacherPatchDTO);

        teacherService.updateByUid(new UpdatePersonData<>(uid, newTeacher, bindingResult));
    }

    @DeleteMapping("/delete")
    public void deleteTeacher(@RequestParam("uid") Long uid) {
        teacherService.deleteByUId(uid);
    }

    @DeleteMapping("/soft/delete")
    public void softDeleteTeacher(@RequestParam("uid") Long uid) {
        teacherService.softDeleteByUId(uid);
    }
}

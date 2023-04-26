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

    @GetMapping("/{uid}")
    @ResponseStatus(HttpStatus.FOUND)
    public TeacherGetDTO findTeacherByUid(@PathVariable("uid") Long uid) {
        return teacherMapper.toTeacherGetDTO(teacherService.findByUid(uid));
    }

    @GetMapping("/emails/{email}")
    @ResponseStatus(HttpStatus.FOUND)
    public List<TeacherSlimGetDTO> findSlimTeacherByEmail(@PathVariable("email") String email) {
        return teacherListMapper.toTeachersSlimGetDTO(teacherService.findByEmail(email));
    }

    @GetMapping("surnames/{surname}")
    @ResponseStatus(HttpStatus.FOUND)
    public List<TeacherSlimGetDTO> findSlimTeacherBySurname(@PathVariable("surname") String surname) {
        return teacherListMapper.toTeachersSlimGetDTO(teacherService.findBySurname(surname));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.FOUND)
    public List<TeacherSlimGetDTO> findAllSlimTeachers(@RequestParam(value = "page", required = false) Integer page,
                                                       @RequestParam(value = "peoplePerPage", required = false) Integer peoplePerPage,
                                                       @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                       @RequestParam(value = "faculty", required = false) Long facultyId) {
        return teacherListMapper.toTeachersSlimGetDTO(teacherService.findAllPeople(new FindAllData(page, peoplePerPage, deleted, facultyId)));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewTeacher(@RequestBody @Valid TeacherPostDTO teacherPostDTO,
                                                       BindingResult bindingResult) {
        Teacher newTeacher = teacherMapper.toTeacher(teacherPostDTO);

        teacherService.register(new RegisterPersonData<>(newTeacher, bindingResult));
    }

    @PatchMapping("{uid}/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateTeacher(@PathVariable("uid") Long uid,
                              @RequestBody @Valid  TeacherPatchDTO teacherPatchDTO,
                                                   BindingResult bindingResult) {
        Teacher newTeacher = teacherMapper.toTeacher(teacherPatchDTO);

        teacherService.update(new UpdatePersonData<>(uid, newTeacher, bindingResult));
    }

    @DeleteMapping("{uid}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeacher(@PathVariable("uid") Long uid) {
        teacherService.delete(uid);
    }

    @DeleteMapping("{uid}/softDelete")
    @ResponseStatus(HttpStatus.OK)
    public void softDeleteTeacher(@PathVariable("uid") Long uid) {
        teacherService.softDelete(uid);
    }
}

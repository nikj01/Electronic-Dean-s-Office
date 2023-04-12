package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.StudentGroupListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.StudentGroupMapper;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.RegisterStudentGroupData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.UpdateStudentGroupData;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/studentGroups")
public class StudentGroupsController {
    private final StudentGroupService studentGroupService;
    private final StudentGroupMapper studentGroupMapper;
    private final StudentGroupListMapper studentGroupListMapper;

    @Autowired
    public StudentGroupsController(StudentGroupService studentGroupService,
                                   StudentGroupMapper studentGroupMapper,
                                   StudentGroupListMapper studentGroupListMapper) {
        this.studentGroupService = studentGroupService;
        this.studentGroupMapper = studentGroupMapper;
        this.studentGroupListMapper = studentGroupListMapper;
    }

    @GetMapping("/findByName")
    @ResponseStatus(HttpStatus.FOUND)
    public List<StudentGroupSlimGetDTO> findStudentGroupByName(@RequestParam("name") String name) {
        return studentGroupListMapper.toStudentGroupsSlimGetDTO(studentGroupService.findByName(name));
    }

    @GetMapping()
    public List<StudentGroupSlimGetDTO> findAllSlimStudentGroups(@RequestParam(value = "page", required = false) Integer page,
                                                                 @RequestParam(value = "groupsPerPage", required = false) Integer groupsPerPage,
                                                                 @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                                 @RequestParam(value = "faculty", required = false) String facultyName) {
        return studentGroupListMapper.toStudentGroupsSlimGetDTO(studentGroupService.findAll(new FindAllData(page, groupsPerPage, deleted, facultyName)));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewStudentGroup(@RequestBody @Valid StudentGroupPostDTO newPostStudentGroup,
                                                            BindingResult bindingResult) {
        StudentGroup studentGroup = studentGroupMapper.toStudentGroup(newPostStudentGroup);

        studentGroupService.registerNew(new RegisterStudentGroupData(studentGroup, bindingResult));
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateStudentGroup(@RequestParam("name") String studentGroupName,
                                   @RequestBody @Valid   StudentGroupPatchDTO studentGroupPatchDTO) {
        StudentGroup studentGroup = studentGroupMapper.toStudentGroup(studentGroupPatchDTO);

        studentGroupService.update(new UpdateStudentGroupData(studentGroupName, studentGroup));
    }

    @DeleteMapping("/delete")
    public void deleteStudentGroup(@RequestParam("name") String name) {
        studentGroupService.delete(name);
    }

    @DeleteMapping("/soft/delete")
    public void softDeleteStudentGroup(@RequestParam("name") String name) {
        studentGroupService.softDelete(name);
    }

}

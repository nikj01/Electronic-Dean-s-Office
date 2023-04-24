package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.StudentGroupListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.StudentGroupMapper;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.RegisterStudentGroupData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.UpdateStudentGroupData;
import ua.dgma.electronicDeansOffice.services.interfaces.ReportsAnalyzer;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/studentGroups")
public class StudentGroupsController {
    private final StudentGroupService studentGroupService;
    private final ReportsAnalyzer reportsAnalyzer;
    private final StudentGroupMapper studentGroupMapper;
    private final StudentGroupListMapper studentGroupListMapper;

    @Autowired
    public StudentGroupsController(StudentGroupService studentGroupService,
                                   ReportsAnalyzer reportsAnalyzer,
                                   StudentGroupMapper studentGroupMapper,
                                   StudentGroupListMapper studentGroupListMapper) {
        this.studentGroupService = studentGroupService;
        this.reportsAnalyzer = reportsAnalyzer;
        this.studentGroupMapper = studentGroupMapper;
        this.studentGroupListMapper = studentGroupListMapper;
    }

    @GetMapping("/findById")
    @ResponseStatus(HttpStatus.FOUND)
    public StudentGroupGetDTO findStudentGroupById(@RequestParam("id") Long id) {
        return studentGroupMapper.toStudentGroupGetDTO(studentGroupService.findOne(id));
    }

    @GetMapping("/findByName")
    @ResponseStatus(HttpStatus.FOUND)
    public List<StudentGroupSlimGetDTO> findStudentGroupByName(@RequestParam("name") String name) {
        return studentGroupListMapper.toStudentGroupsSlimGetDTO(studentGroupService.findByName(name));
    }

    @GetMapping("/attendance")
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, Double> showGroupAvgAttendance(@RequestParam("id") Long groupId) {
        return reportsAnalyzer.getAvgAttendanceForGroup(groupId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.FOUND)
    public List<StudentGroupSlimGetDTO> findAllSlimStudentGroups(@RequestParam(value = "page", required = false) Integer page,
                                                                 @RequestParam(value = "groupsPerPage", required = false) Integer groupsPerPage,
                                                                 @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                                 @RequestParam(value = "faculty", required = false) Long facultyId) {
        return studentGroupListMapper.toStudentGroupsSlimGetDTO(studentGroupService.findAll(new FindAllData(page, groupsPerPage, deleted, facultyId)));
    }

    @GetMapping("/facultyAttendace")
    @ResponseStatus(HttpStatus.FOUND)
    public Map<Long, Double> showGroupsAvgAttendanceOnFaculty(@RequestParam(value = "page", required = false) Integer page,
                                                              @RequestParam(value = "groupsPerPage", required = false) Integer groupsPerPage,
                                                              @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                              @RequestParam(value = "faculty", required = false) Long facultyId) {
        return reportsAnalyzer.getAvgAttendanceForGroupsOnFaculty(new FindAllData(page, groupsPerPage, deleted, facultyId));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewStudentGroup(@RequestBody @Valid StudentGroupPostDTO newPostStudentGroup,
                                                            BindingResult bindingResult) {
        StudentGroup studentGroup = studentGroupMapper.toStudentGroup(newPostStudentGroup);

        studentGroupService.register(new RegisterStudentGroupData(studentGroup, bindingResult));
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateStudentGroup(@RequestParam("id") Long groupId,
                                   @RequestBody @Valid StudentGroupPatchDTO studentGroupPatchDTO) {
        StudentGroup studentGroup = studentGroupMapper.toStudentGroup(studentGroupPatchDTO);

        studentGroupService.update(new UpdateStudentGroupData(groupId, studentGroup));
    }

    @DeleteMapping("/delete")
    public void deleteStudentGroup(@RequestParam("id") Long groupId) {
        studentGroupService.delete(groupId);
    }

    @DeleteMapping("/soft/delete")
    public void softDeleteStudentGroup(@RequestParam("id") Long groupId) {
        studentGroupService.softDelete(groupId);
    }

}

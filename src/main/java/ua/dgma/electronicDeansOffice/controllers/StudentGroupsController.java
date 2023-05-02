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
import ua.dgma.electronicDeansOffice.security.annotations.*;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.DataForGroupStatistics;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.RegisterStudentGroupData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.UpdateStudentGroupData;
import ua.dgma.electronicDeansOffice.services.interfaces.ReportsAnalyzerForGroups;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static ua.dgma.electronicDeansOffice.utill.ConvertData.convertData;

@RestController
@RequestMapping("/studentGroups")
public class StudentGroupsController {
    private final StudentGroupService studentGroupService;
    private final ReportsAnalyzerForGroups reportsAnalyzer;
    private final StudentGroupMapper studentGroupMapper;
    private final StudentGroupListMapper studentGroupListMapper;

    @Autowired
    public StudentGroupsController(StudentGroupService studentGroupService,
                                   ReportsAnalyzerForGroups reportsAnalyzer,
                                   StudentGroupMapper studentGroupMapper,
                                   StudentGroupListMapper studentGroupListMapper) {
        this.studentGroupService = studentGroupService;
        this.reportsAnalyzer = reportsAnalyzer;
        this.studentGroupMapper = studentGroupMapper;
        this.studentGroupListMapper = studentGroupListMapper;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @AllPerople
    public StudentGroupGetDTO findStudentGroupById(@PathVariable("id") Long id) {
        return studentGroupMapper.toStudentGroupGetDTO(studentGroupService.findOne(id));
    }

    @GetMapping("names/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    @AllPerople
    public List<StudentGroupSlimGetDTO> findStudentGroupByName(@PathVariable("name") String name) {
        return studentGroupListMapper.toStudentGroupsSlimGetDTO(studentGroupService.findByName(name));
    }

    @GetMapping("{id}/attendance")
    @ResponseStatus(HttpStatus.FOUND)
    @AllPerople
    public Map<Long, Double> showGroupAvgAttendance(@PathVariable("id") Long groupId,
                                                    @RequestParam(value = "from", required = false) String searchFrom,
                                                    @RequestParam(value = "to", required = false) String searchTo) {
        return reportsAnalyzer.getAvgAttendanceForGroup(new DataForGroupStatistics(groupId, convertData(searchFrom), convertData(searchTo)));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.FOUND)
    @AllPerople
    public List<StudentGroupSlimGetDTO> findAllSlimStudentGroups(@RequestParam(value = "page", required = false) Integer page,
                                                                 @RequestParam(value = "groupsPerPage", required = false) Integer groupsPerPage,
                                                                 @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                                 @RequestParam(value = "faculty", required = false) Long facultyId) {
        return studentGroupListMapper.toStudentGroupsSlimGetDTO(studentGroupService.findAll(new FindAllData(page, groupsPerPage, deleted, facultyId)));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @IsRoot
    @IsAdmin
    @IsDeaneryWorker
    public void registerNewStudentGroup(@RequestBody @Valid StudentGroupPostDTO newPostStudentGroup,
                                                            BindingResult bindingResult) {
        StudentGroup studentGroup = studentGroupMapper.toStudentGroup(newPostStudentGroup);

        studentGroupService.register(new RegisterStudentGroupData(studentGroup, bindingResult));
    }

    @PatchMapping("{id}/update")
    @ResponseStatus(HttpStatus.OK)
    @IsRoot
    @IsAdmin
    @IsDeaneryWorker
    public void updateStudentGroup(@PathVariable("id") Long groupId,
                                   @RequestBody @Valid StudentGroupPatchDTO studentGroupPatchDTO) {
        StudentGroup studentGroup = studentGroupMapper.toStudentGroup(studentGroupPatchDTO);

        studentGroupService.update(new UpdateStudentGroupData(groupId, studentGroup));
    }

    @DeleteMapping("{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    @IsRoot
    @IsAdmin
    public void deleteStudentGroup(@PathVariable("id") Long groupId) {
        studentGroupService.delete(groupId);
    }

    @DeleteMapping("{id}/softDelete")
    @ResponseStatus(HttpStatus.OK)
    @IsRoot
    @IsAdmin
    @IsDeaneryWorker
    public void softDeleteStudentGroup(@PathVariable("id") Long groupId) {
        studentGroupService.softDelete(groupId);
    }

}

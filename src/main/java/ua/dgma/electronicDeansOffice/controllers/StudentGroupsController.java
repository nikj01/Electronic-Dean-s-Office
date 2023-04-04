package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.exceptions.CustomException;
import ua.dgma.electronicDeansOffice.exceptions.ErrorResponse;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.StudentGroupListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.StudentGroupMapper;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
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
    public StudentGroupGetDTO findStudentGroupByName(@RequestParam("name") String name) {
        return studentGroupMapper.toStudentGroupGetDTO(studentGroupService.findByName(name));
    }

    @GetMapping("/slim/findByName")
    @ResponseStatus(HttpStatus.FOUND)
    public StudentGroupSlimGetDTO findSlimStudentGroupByName(@RequestParam("name") String name) {
        return studentGroupMapper.toStudentGroupSlimGetDTO(studentGroupService.findByName(name));
    }

    @GetMapping()
    public List<StudentGroupGetDTO> findAllStudentGroups(@RequestParam(value = "page", required = false) Integer page,
                                                         @RequestParam(value = "groupsPerPage", required = false) Integer groupsPerPage,
                                                         @RequestParam(value = "isDeleted", required = false, defaultValue = "false") Boolean isDeleted) {
        return studentGroupListMapper.toStudentGroupsGetDTO(studentGroupService.findAllWithPaginationOrWithout(page, groupsPerPage, isDeleted));
    }

    @GetMapping("/slim")
    public List<StudentGroupSlimGetDTO> findAllSlimStudentGroups(@RequestParam(value = "page", required = false) Integer page,
                                                                 @RequestParam(value = "groupsPerPage", required = false) Integer groupsPerPage,
                                                                 @RequestParam(value = "isDeleted", required = false, defaultValue = "false") Boolean isDeleted) {
        return studentGroupListMapper.toStudentGroupsSlimGetDTO(studentGroupService.findAllWithPaginationOrWithout(page, groupsPerPage, isDeleted));
    }

    @GetMapping("/findAllGroupsByCurator")
    public List<StudentGroupGetDTO> findAllStudentGroupsByCurator(@RequestParam(value = "curatorUid") Long curatorUid,
                                                                  @RequestParam(value = "isDeleted", required = false, defaultValue = "false") Boolean isDeleted) {
        return studentGroupListMapper.toStudentGroupsGetDTO(studentGroupService.findAllGroupsByCurator(curatorUid, isDeleted));
    }

    @GetMapping("/slim/findAllGroupsByCurator")
    public List<StudentGroupSlimGetDTO> findAllSlimStudentGroupsByCurator(@RequestParam(value = "curatorUid") Long curatorUid,
                                                                          @RequestParam(value = "isDeleted", required = false, defaultValue = "false") Boolean isDeleted) {
        return studentGroupListMapper.toStudentGroupsSlimGetDTO(studentGroupService.findAllGroupsByCurator(curatorUid, isDeleted));
    }

    @GetMapping("/findAllGroupsByDepartment")
    public List<StudentGroupGetDTO> findAllStudentGroupsByDepartment(@RequestParam(value = "department") String departmentName,
                                                                     @RequestParam(value = "isDeleted", required = false, defaultValue = "false") Boolean isDeleted) {
        return studentGroupListMapper.toStudentGroupsGetDTO(studentGroupService.findAllGroupsByDepartment(departmentName, isDeleted));
    }

    @GetMapping("/slim/findAllGroupsByDepartment")
    public List<StudentGroupSlimGetDTO> findAllSlimStudentGroupsByDepartment(@RequestParam(value = "department") String departmentName,
                                                                             @RequestParam(value = "isDeleted", required = false, defaultValue = "false") Boolean isDeleted) {
        return studentGroupListMapper.toStudentGroupsSlimGetDTO(studentGroupService.findAllGroupsByDepartment(departmentName, isDeleted));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewStudentGroup(@RequestBody @Valid StudentGroupPostDTO newPostStudentGroup,
                                                            BindingResult bindingResult) {
        StudentGroup studentGroup = studentGroupMapper.toStudentGroup(newPostStudentGroup);

        studentGroupService.registerNew(studentGroup, bindingResult);
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateStudentGroup(@RequestParam("name") String studentGroupName,
                                   @RequestBody @Valid   StudentGroupPatchDTO studentGroupPatchDTO,
                                                         BindingResult bindingResult) {
        StudentGroup studentGroup = studentGroupMapper.toStudentGroup(studentGroupPatchDTO);

        studentGroupService.updateByName(studentGroupName, studentGroup, bindingResult);
    }

    @DeleteMapping("/delete")
    public void deleteStudentGroup(@RequestParam("name") String name) {
        studentGroupService.deleteByName(name);
    }

    @DeleteMapping("/soft/delete")
    public void softDeleteStudentGroup(@RequestParam("name") String name) {
        studentGroupService.softDeleteByName(name);
    }

//    @ExceptionHandler
//    private ResponseEntity<ErrorResponse> handleException(CustomException e) {
//        ErrorResponse response = new ErrorResponse(
//                e.getMessage(),
//                System.currentTimeMillis()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    private ResponseEntity<ErrorResponse> handleException(RuntimeException e) {
//        ErrorResponse response = new ErrorResponse(
//                e.getMessage(),
//                System.currentTimeMillis()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    private ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
//        ErrorResponse response = new ErrorResponse(
//                e.getMessage(),
//                System.currentTimeMillis()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }

}

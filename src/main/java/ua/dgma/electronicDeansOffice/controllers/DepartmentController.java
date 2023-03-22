package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.exceptions.CustomException;
import ua.dgma.electronicDeansOffice.exceptions.ErrorResponse;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.DepartmentListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.DepartmentMapper;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.services.impl.DepartmentServiceImpl;
import ua.dgma.electronicDeansOffice.services.interfaces.DepartmentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;
    private final DepartmentListMapper departmentListMapper;

    @Autowired
    public DepartmentController(DepartmentService departmentService,
                                DepartmentMapper departmentMapper,
                                DepartmentListMapper departmentListMapper) {
        this.departmentService = departmentService;
        this.departmentMapper = departmentMapper;
        this.departmentListMapper = departmentListMapper;
    }

    @GetMapping("/findByName")
    public DepartmentGetDTO findDepartmentByName(@RequestParam("name") String name) {
        return departmentMapper.toDepartmentGetDTO(departmentService.findByName(name));
    }

    @GetMapping("/slim/findByName")
    public DepartmentSlimGetDTO findSlimDepartmentByName(@RequestParam("name") String name) {
        return departmentMapper.toDepartmentSlimGetDTO(departmentService.findByName(name));
    }

    @GetMapping()
    public List<DepartmentGetDTO> findAllDepartments(@RequestParam(value = "page", required = false) Integer page,
                                          @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
        return departmentListMapper.toDepartmentsGetDTO(departmentService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @GetMapping("/slim")
    public List<DepartmentSlimGetDTO> findAllSlimDepartments(@RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
        return departmentListMapper.toDepartmentsSlimGetDTO(departmentService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @GetMapping("/findByFacultyName")
    public List<DepartmentSlimGetDTO> findAllByFacultyName(@RequestParam("facultyName") String facultyName) {
        return departmentListMapper.toDepartmentsSlimGetDTO((List<Department>) departmentService.findAllDepartmentsByFacultyName(facultyName));
    }

    @GetMapping("/register")
    public void registerNewDepartment(@RequestBody @Valid DepartmentPostDTO newPostDepartment,
                                                          BindingResult bindingResult) {
        Department newDepartment = departmentMapper.toDepartment(newPostDepartment);

        departmentService.registerNew(newDepartment, bindingResult);
    }

    @PatchMapping("/update")
    public void updateDepartment(@RequestParam("name") String name,
                                 @RequestBody @Valid   DepartmentPostDTO updatedPostDepartment,
                                                       BindingResult bindingResult) {
        Department updatedDepartment = departmentMapper.toDepartment(updatedPostDepartment);

        departmentService.updateByName(name, updatedDepartment, bindingResult);
    }

    @DeleteMapping("/delete")
    public void deleteDepartment(@RequestParam("name") String name) {
        departmentService.deleteByName(name);
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

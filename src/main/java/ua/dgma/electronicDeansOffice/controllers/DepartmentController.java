package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentsGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentsSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.impl.DepartmentMapperImpl;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.services.impl.DepartmentServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;
    private final DepartmentMapperImpl departmentMapper;

    @Autowired
    public DepartmentController(DepartmentServiceImpl departmentService, DepartmentMapperImpl departmentMapper) {
        this.departmentService = departmentService;
        this.departmentMapper = departmentMapper;
    }

    @GetMapping("/findByName")
    public DepartmentGetDTO findDepartmentByName(@RequestParam("name") String name) {
        return departmentMapper.convertToDepartmentGetDTO(departmentService.findByName(name));
    }

    @GetMapping("/slim/findByName")
    public DepartmentSlimGetDTO findSlimDepartmentByName(@RequestParam("name") String name) {
        return departmentMapper.convertToDepartmentSlimGetDTO(departmentService.findByName(name));
    }

    @GetMapping()
    public DepartmentsGetDTO findAll(@RequestParam(value = "page", required = false) Integer page,
                                     @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
        return departmentMapper.convertToDepartmentsGetDTO(departmentService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @GetMapping("/slim")
    public DepartmentsSlimGetDTO findAllSlim(@RequestParam(value = "page", required = false) Integer page,
                                             @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
        return departmentMapper.convertToDepartmentsSlimGetDTO(departmentService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @GetMapping("/findByFacultyName")
    public DepartmentsSlimGetDTO findAllByFacultyName(@RequestParam("facultyName") String facultyName) {
        return departmentMapper.convertToDepartmentsSlimGetDTO((List<Department>) departmentService.findAllDepartmentsByFacultyName(facultyName));
    }

}

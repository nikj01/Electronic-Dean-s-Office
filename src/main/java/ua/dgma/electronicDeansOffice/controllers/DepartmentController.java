package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.DepartmentListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.DepartmentMapper;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.services.impl.DepartmentService;

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
    public List<DepartmentGetDTO> findAll(@RequestParam(value = "page", required = false) Integer page,
                                          @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
        return departmentListMapper.toDepartmentsGetDTO(departmentService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @GetMapping("/slim")
    public List<DepartmentSlimGetDTO> findAllSlim(@RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
        return departmentListMapper.toDepartmentsSlimGetDTO(departmentService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @GetMapping("/findByFacultyName")
    public List<DepartmentSlimGetDTO> findAllByFacultyName(@RequestParam("facultyName") String facultyName) {
        return departmentListMapper.toDepartmentsSlimGetDTO((List<Department>) departmentService.findAllDepartmentsByFacultyName(facultyName));
    }

}

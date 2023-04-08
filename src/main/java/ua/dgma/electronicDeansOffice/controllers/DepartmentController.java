package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.DepartmentListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.DepartmentMapper;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.department.RegisterDepartmentData;
import ua.dgma.electronicDeansOffice.services.impl.data.department.UpdateDepartmentData;
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
    public List<DepartmentSlimGetDTO> findAllSlimDepartments(@RequestParam(value = "page", required = false) Integer page,
                                                             @RequestParam(value = "departmentsPerPage", required = false) Integer departmentsPerPage,
                                                             @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                             @RequestParam(value = "faculty", required = false) String facultyName) {
        return departmentListMapper.toDepartmentsSlimGetDTO(departmentService.findAllDepartments(new FindAllData(page, departmentsPerPage, deleted, facultyName)));
    }

    @PostMapping("/register")
    public void registerNewDepartment(@RequestBody @Valid DepartmentPostDTO newPostDepartment,
                                                          BindingResult bindingResult) {
        Department newDepartment = departmentMapper.toDepartment(newPostDepartment);

        departmentService.registerNew(new RegisterDepartmentData(newDepartment, bindingResult));
    }

    @PatchMapping("/update")
    public void updateDepartment(@RequestParam("name") String name,
                                 @RequestBody @Valid   DepartmentPatchDTO departmentPatchDTO) {
        Department updatedDepartment = departmentMapper.toDepartment(departmentPatchDTO);

        departmentService.updateByName(new UpdateDepartmentData(name, updatedDepartment));
    }

    @DeleteMapping("/delete")
    public void deleteDepartment(@RequestParam("name") String name) {
        departmentService.deleteByName(name);
    }

    @DeleteMapping("/soft/delete")
    public void softDeleteDepartment(@RequestParam("name") String name) {
        departmentService.softDeleteByName(name);
    }
}

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

    @GetMapping("/findById")
    public DepartmentGetDTO findDepartmentById(@RequestParam("id") Long id) {
        return departmentMapper.toDepartmentGetDTO(departmentService.findOne(id));
    }

    @GetMapping("/findByName")
    public List<DepartmentSlimGetDTO> findDepartmentByName(@RequestParam("name") String name) {
        return departmentListMapper.toDepartmentsSlimGetDTO(departmentService.findByName(name));
    }

    @GetMapping()
    public List<DepartmentSlimGetDTO> findAllSlimDepartments(@RequestParam(value = "page", required = false) Integer page,
                                                             @RequestParam(value = "departmentsPerPage", required = false) Integer departmentsPerPage,
                                                             @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                             @RequestParam(value = "faculty", required = false) Long facultyId) {
        return departmentListMapper.toDepartmentsSlimGetDTO(departmentService.findAllDepartments(new FindAllData(page, departmentsPerPage, deleted, facultyId)));
    }

    @PostMapping("/register")
    public void registerNewDepartment(@RequestBody @Valid DepartmentPostDTO newPostDepartment,
                                                          BindingResult bindingResult) {
        Department newDepartment = departmentMapper.toDepartment(newPostDepartment);

        departmentService.register(new RegisterDepartmentData(newDepartment, bindingResult));
    }

    @PatchMapping("/update")
    public void updateDepartment(@RequestParam("id") Long id,
                                 @RequestBody @Valid DepartmentPatchDTO departmentPatchDTO) {
        Department updatedDepartment = departmentMapper.toDepartment(departmentPatchDTO);

        departmentService.update(new UpdateDepartmentData(id, updatedDepartment));
    }

    @DeleteMapping("/delete")
    public void deleteDepartment(@RequestParam("id") Long departmentId) {
        departmentService.delete(departmentId);
    }

    @DeleteMapping("/soft/delete")
    public void softDeleteDepartment(@RequestParam("id") Long departmentId) {
        departmentService.softDelete(departmentId);
    }
}

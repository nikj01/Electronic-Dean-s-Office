package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public DepartmentGetDTO findDepartmentById(@PathVariable("id") Long id) {
        return departmentMapper.toDepartmentGetDTO(departmentService.findOne(id));
    }

    @GetMapping("names/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    public List<DepartmentSlimGetDTO> findDepartmentByName(@PathVariable("name") String name) {
        return departmentListMapper.toDepartmentsSlimGetDTO(departmentService.findByName(name));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.FOUND)
    public List<DepartmentSlimGetDTO> findAllSlimDepartments(@RequestParam(value = "page", required = false) Integer page,
                                                             @RequestParam(value = "departmentsPerPage", required = false) Integer departmentsPerPage,
                                                             @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                             @RequestParam(value = "faculty", required = false) Long facultyId) {
        return departmentListMapper.toDepartmentsSlimGetDTO(departmentService.findAllDepartments(new FindAllData(page, departmentsPerPage, deleted, facultyId)));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewDepartment(@RequestBody @Valid DepartmentPostDTO newPostDepartment,
                                                          BindingResult bindingResult) {
        Department newDepartment = departmentMapper.toDepartment(newPostDepartment);

        departmentService.register(new RegisterDepartmentData(newDepartment, bindingResult));
    }

    @PatchMapping("{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateDepartment(@PathVariable("id") Long id,
                                 @RequestBody @Valid DepartmentPatchDTO departmentPatchDTO) {
        Department updatedDepartment = departmentMapper.toDepartment(departmentPatchDTO);

        departmentService.update(new UpdateDepartmentData(id, updatedDepartment));
    }

    @DeleteMapping("{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDepartment(@PathVariable("id") Long departmentId) {
        departmentService.delete(departmentId);
    }

    @DeleteMapping("{id}/softDelete")
    @ResponseStatus(HttpStatus.OK)
    public void softDeleteDepartment(@PathVariable("id") Long departmentId) {
        departmentService.softDelete(departmentId);
    }
}

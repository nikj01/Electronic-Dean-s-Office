package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.*;
import ua.dgma.electronicDeansOffice.models.Department;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DepartmentMapper {
    @Named("postDepartment")
    Department toDepartment(DepartmentPostDTO departmentPostDTO);
    @Named("patchDepartment")
    Department toDepartment(DepartmentPatchDTO departmentPatchDTO);
    @Named("department")
    DepartmentGetDTO toDepartmentGetDTO(Department department);
    @Named("slimDepartment")
    DepartmentSlimGetDTO toDepartmentSlimGetDTO(Department department);
}

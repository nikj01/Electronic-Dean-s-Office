package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.StudentGroupListMapper;
import ua.dgma.electronicDeansOffice.models.Department;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StudentGroupMapper.class, StudentGroupListMapper.class, FacultyMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DepartmentMapper {

    Department toDepartment(DepartmentPostDTO departmentPost);
    @Named("department")
    DepartmentGetDTO toDepartmentGetDTO(Department department);
    @Named("slimDepartment")
    DepartmentSlimGetDTO toDepartmentSlimGetDTO(Department department);

}

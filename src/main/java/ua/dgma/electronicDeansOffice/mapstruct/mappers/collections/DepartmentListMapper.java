package ua.dgma.electronicDeansOffice.mapstruct.mappers.collections;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.DepartmentMapper;
import ua.dgma.electronicDeansOffice.models.Department;

import java.util.List;

@Mapper(componentModel = "spring", uses = DepartmentMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DepartmentListMapper {

    @IterableMapping(qualifiedByName = "department")
    List<DepartmentGetDTO> toDepartmentsGetDTO(List<Department> departments);
    @IterableMapping(qualifiedByName = "slimDepartment")
    List<DepartmentSlimGetDTO> toDepartmentsSlimGetDTO(List<Department> departments);


}

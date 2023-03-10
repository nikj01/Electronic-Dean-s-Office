package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.*;
import ua.dgma.electronicDeansOffice.models.Department;

import java.util.List;
import java.util.Set;

public interface DepartmentMapper {

    Department convertToDepartment(DepartmentPostDTO departmentPost);

    DepartmentGetDTO convertToDepartmentGetDTO(Department department);

    DepartmentSlimGetDTO convertToDepartmentSlimGetDTO(Department department);

    DepartmentsGetDTO convertToDepartmentsGetDTO(List<Department> departments);

//    DepartmentsSlimGetDTO convertToDepartmentsSlimGetDTO(List<Department> departments);
    DepartmentsSlimGetDTO convertToDepartmentsSlimGetDTO(List<Department> departments);
}

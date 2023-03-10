package ua.dgma.electronicDeansOffice.mapstruct.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.DepartmentMapper;
import ua.dgma.electronicDeansOffice.models.Department;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentMapperImpl implements DepartmentMapper {

    private final ModelMapper mapper;

    @Autowired
    public DepartmentMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Department convertToDepartment(DepartmentPostDTO departmentPost) {
        return mapper.map(departmentPost, Department.class);
    }

    @Override
    public DepartmentGetDTO convertToDepartmentGetDTO(Department department) {
        return mapper.map(department, DepartmentGetDTO.class);
    }

    @Override
    public DepartmentSlimGetDTO convertToDepartmentSlimGetDTO(Department department) {
        return mapper.map(department, DepartmentSlimGetDTO.class);
    }

    @Override
    public DepartmentsGetDTO convertToDepartmentsGetDTO(List<Department> departments) {
        return new DepartmentsGetDTO(departments.stream()
                                                .map(this::convertToDepartmentGetDTO)
                                                .sorted()
                                                .collect(Collectors.toList()));
    }

    @Override
    public DepartmentsSlimGetDTO convertToDepartmentsSlimGetDTO(List<Department> departments) {
        return new DepartmentsSlimGetDTO(departments.stream()
                                                    .map(this::convertToDepartmentSlimGetDTO)
                                                    .sorted()
                                                    .collect(Collectors.toList()));
    }
}

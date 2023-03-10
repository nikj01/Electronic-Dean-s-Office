package ua.dgma.electronicDeansOffice.mapstruct.mappers.impl;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentsSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.FacultyMapper;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Faculty;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FacultyMapperImpl implements FacultyMapper {

    private final ModelMapper mapper;
    private final DepartmentMapperImpl departmentMapper;

    @Autowired
    public FacultyMapperImpl(ModelMapper mapper, DepartmentMapperImpl departmentMapper) {
        this.mapper = mapper;
        this.departmentMapper = departmentMapper;
    }


    @Override
    public Faculty convertToFaculty(FacultyPostDTO facultyPost) {
        return mapper.map(facultyPost, Faculty.class);
    }

    @Override
    public FacultyGetDTO convertToFacultyGetDTO(Faculty faculty) {
        return mapper.map(faculty, FacultyGetDTO.class);
    }

    @Override
    public FacultySlimGetDTO convertToFacultySlimGetDTO(Faculty faculty) {
        return mapper.map(faculty, FacultySlimGetDTO.class);
    }

    @Override
    public FacultiesGetDTO convertToFacultiesGetDTO(List<Faculty> faculties) {
        return new FacultiesGetDTO(faculties.stream()
                                            .map(this::convertToFacultyGetDTO)
                                            .sorted()
                                            .collect(Collectors.toList()));
    }

    @Override
    public FacultiesSlimGetDTO convertToFacultiesSlimGetDTO(List<Faculty> faculties) {
        return new FacultiesSlimGetDTO(faculties.stream()
                                                .map(this::convertToFacultySlimGetDTO)
                                                .sorted()
                                                .collect(Collectors.toList()));
    }
}

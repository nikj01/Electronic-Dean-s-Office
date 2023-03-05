package ua.dgma.electronicDeansOffice.mapstruct.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.FacultyMapper;
import ua.dgma.electronicDeansOffice.models.Faculty;

import java.util.List;

@Component
public class FacultyMapperImpl implements FacultyMapper {

    private final ModelMapper mapper;

    @Autowired
    public FacultyMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
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
        return null;
    }

    @Override
    public FacultiesSlimGetDTO convertToFacultiesSlimGetDTO(List<Faculty> faculties) {
        return null;
    }
}

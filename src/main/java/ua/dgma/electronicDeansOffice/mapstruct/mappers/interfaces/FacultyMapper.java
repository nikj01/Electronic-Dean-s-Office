package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentsSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.*;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Faculty;

import java.util.List;

public interface FacultyMapper {

    Faculty convertToFaculty(FacultyPostDTO facultyPost);

    FacultyGetDTO convertToFacultyGetDTO(Faculty faculty);

    FacultySlimGetDTO convertToFacultySlimGetDTO(Faculty faculty);

    FacultiesGetDTO convertToFacultiesGetDTO(List<Faculty> faculties);

    FacultiesSlimGetDTO convertToFacultiesSlimGetDTO(List<Faculty> faculties);
}

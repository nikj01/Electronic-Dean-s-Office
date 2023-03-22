package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.*;
import ua.dgma.electronicDeansOffice.models.Faculty;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FacultyMapper {

    Faculty toFaculty(FacultyPostDTO facultyPost);
    FacultyGetDTO toFacultyGetDTO(Faculty faculty);
    FacultySlimGetDTO toFacultySlimGetDTO(Faculty faculty);


}

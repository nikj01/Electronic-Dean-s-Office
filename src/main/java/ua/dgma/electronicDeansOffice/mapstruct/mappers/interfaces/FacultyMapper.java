package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.*;
import ua.dgma.electronicDeansOffice.models.Faculty;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FacultyMapper {
    @Named(value = "postFaculty")
    Faculty toFaculty(FacultyPostDTO facultyPost);
    @Named(value = "patchFaculty")
    Faculty toFaculty(FacultyPatchDTO facultyPatch);
    FacultyGetDTO toFacultyGetDTO(Faculty faculty);
    FacultySlimGetDTO toFacultySlimGetDTO(Faculty faculty);


}

package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultyGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultyPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultyPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.models.Faculty;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FacultyMapper {
    @Named("postFaculty")
    Faculty toFaculty(FacultyPostDTO facultyPost);
    @Named("patchFaculty")
    Faculty toFaculty(FacultyPatchDTO facultyPatch);
    @Named("faculty")
    FacultyGetDTO toFacultyGetDTO(Faculty faculty);
    @Named("slimFaculty")
    FacultySlimGetDTO toFacultySlimGetDTO(Faculty faculty);


}

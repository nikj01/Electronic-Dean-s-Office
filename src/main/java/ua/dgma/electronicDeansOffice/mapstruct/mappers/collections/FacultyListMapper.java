package ua.dgma.electronicDeansOffice.mapstruct.mappers.collections;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.FacultyMapper;
import ua.dgma.electronicDeansOffice.models.Faculty;

import java.util.List;

@Mapper(componentModel = "spring", uses = FacultyMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FacultyListMapper {
    @IterableMapping(qualifiedByName = "slimFaculty")
    List<FacultySlimGetDTO> toFacultiesSlimGetDTO(List<Faculty> faculties);
}

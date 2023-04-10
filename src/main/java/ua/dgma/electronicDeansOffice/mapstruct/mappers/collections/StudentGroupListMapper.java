package ua.dgma.electronicDeansOffice.mapstruct.mappers.collections;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.DepartmentMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.PersonMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.StudentGroupMapper;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

import java.util.List;

@Mapper(componentModel = "spring", uses = StudentGroupMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StudentGroupListMapper {
    @IterableMapping(qualifiedByName = "group")
    List<StudentGroupGetDTO> toStudentGroupsGetDTO(List<StudentGroup> studentGroups);
    @IterableMapping(qualifiedByName = "slimGroup")
    List<StudentGroupSlimGetDTO> toStudentGroupsSlimGetDTO(List<StudentGroup> studentGroups);
}

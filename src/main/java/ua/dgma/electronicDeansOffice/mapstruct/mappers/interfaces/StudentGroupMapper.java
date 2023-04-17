package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface StudentGroupMapper {
    @Named("postGroup")
    StudentGroup toStudentGroup(StudentGroupPostDTO studentGroupPostDTO);
    @Named("patchGroup")
    StudentGroup toStudentGroup(StudentGroupPatchDTO studentGroupPatchDTO);
    @Named("group")
    StudentGroupGetDTO toStudentGroupGetDTO(StudentGroup studentGroup);
    @Named("slimGroup")
    StudentGroupSlimGetDTO toStudentGroupSlimGetDTO(StudentGroup studentGroup);

}

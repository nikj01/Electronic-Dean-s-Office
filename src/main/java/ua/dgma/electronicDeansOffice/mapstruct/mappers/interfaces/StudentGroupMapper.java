package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.StudentListMapper;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

import java.util.List;
@Mapper(componentModel = "spring", uses = {StudentMapper.class, StudentListMapper.class, PersonMapper.class, DepartmentMapper.class}, injectionStrategy = InjectionStrategy.FIELD)
public interface StudentGroupMapper {

    @Named(value = "postGroup")
    StudentGroup toStudentGroup(StudentGroupPostDTO studentGroup);
    @Named(value = "patchGroup")
    StudentGroup toStudentGroup(StudentGroupPatchDTO studentGroup);
    @Named(value = "group")
    StudentGroupGetDTO toStudentGroupGetDTO(StudentGroup studentGroup);
    @Named(value = "slimGroup")
    StudentGroupSlimGetDTO toStudentGroupSlimGetDTO(StudentGroup studentGroup);

}

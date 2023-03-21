package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.*;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

import java.util.List;

public interface StudentGroupMapper {

    StudentGroup convertToStudentGroup(StudentGroupPostDTO studentGroup);

    StudentGroupGetDTO convertToStudentGroupGetDTO(StudentGroup studentGroup);

    StudentGroupSlimGetDTO convertToStudentGroupSlimGetDTO(StudentGroup studentGroup);

    StudentGroupsGetDTO convertToStudentGroupsGetDTO(List<StudentGroup> studentGroups);

    StudentGroupsSlimGetDTO convertToStudentGroupsSlimGetDTO(List<StudentGroup> studentGroups);
}

package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

public interface StudentGroupMapper {

    StudentGroupSlimGetDTO convertToStudentGroupSlim(StudentGroup studentGroup);
}

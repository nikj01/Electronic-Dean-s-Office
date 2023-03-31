package ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

import java.util.List;
import java.util.Set;

@Data
public class TeacherGetDTO extends TeacherSlimGetDTO{

    private Set<StudentGroupSlimGetDTO> studentGroups;
}

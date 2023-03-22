package ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup;

import lombok.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentsSlimGetDTO;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class StudentGroupsGetDTO extends StudentGroupSlimGetDTO {

    private List<StudentGroupGetDTO> studentGroups;
}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.student;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class StudentGetDTO extends PersonGetDTO {

    @NotEmpty
    private StudentGroupSlimGetDTO studentGroup;
}

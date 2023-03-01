package ua.dgma.electronicDeansOffice.mapstruct.dtos.student;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StudentSlimGetDTO extends PersonSlimGetDTO {

    @NotNull
    private StudentGroupSlimGetDTO group;
}

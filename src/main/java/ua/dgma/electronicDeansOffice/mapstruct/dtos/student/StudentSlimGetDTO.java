package ua.dgma.electronicDeansOffice.mapstruct.dtos.student;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StudentSlimGetDTO extends PersonSlimGetDTO {

    @NotBlank
    private StudentGroupSlimGetDTO group;
}

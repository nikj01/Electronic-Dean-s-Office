package ua.dgma.electronicDeansOffice.mapstruct.dtos.student;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimDTO;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StudentSlimGetDTO extends PersonSlimGetDTO {

    @NotBlank
    private StudentGroupSlimDTO group;
}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.student;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupDTO;
import ua.dgma.electronicDeansOffice.models.PersonRole;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StudentGetDTO extends PersonGetDTO {

    @NotEmpty
    private StudentGroupDTO studentGroup;
}

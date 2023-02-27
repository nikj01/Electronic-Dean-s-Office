package ua.dgma.electronicDeansOffice.mapstruct.dtos.student;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimPostDTO;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StudentPostDTO extends PersonPostDTO {

    @NotNull
    private StudentGroupSlimPostDTO studentGroup;
}

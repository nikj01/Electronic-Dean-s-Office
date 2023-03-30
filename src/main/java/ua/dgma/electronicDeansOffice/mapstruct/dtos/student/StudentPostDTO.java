package ua.dgma.electronicDeansOffice.mapstruct.dtos.student;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupPostDTO;

import javax.validation.constraints.NotNull;

@Data
public class StudentPostDTO extends PersonPostDTO {

    private StudentGroupPostDTO studentGroup;
}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.student;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;

import javax.validation.constraints.NotEmpty;

@Data
public class StudentGetDTO extends PersonGetDTO {

    private StudentGroupSlimGetDTO studentGroup;

}

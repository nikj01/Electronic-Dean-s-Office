package ua.dgma.electronicDeansOffice.mapstruct.dtos.student;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;

import javax.validation.constraints.NotNull;

@Data
public class StudentSlimGetDTO extends PersonSlimGetDTO {

}

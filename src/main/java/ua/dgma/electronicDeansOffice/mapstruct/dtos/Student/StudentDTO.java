package ua.dgma.electronicDeansOffice.mapstruct.dtos.Student;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.StudentGroup.StudentGroupDTO;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class StudentDTO extends PersonDTO {

    @NotEmpty(message = "The field |STUDENT GROUP| group cannot be empty!")
    private StudentGroupDTO studentGroup;
}

package ua.dgma.electronicDeansOffice.dtos;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class StudentDTO extends PersonDTO{

    @NotEmpty(message = "The field |STUDENT GROUP| group cannot be empty!")
    private StudentGroupDTO studentGroup;
}

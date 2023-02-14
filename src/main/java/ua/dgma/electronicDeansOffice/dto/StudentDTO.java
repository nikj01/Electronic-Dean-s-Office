package ua.dgma.electronicDeansOffice.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class StudentDTO extends PersonDTO{

    @NonNull
    @NotEmpty(message = "The field |STUDENT GROUP| group cannot be empty!")
    private StudentGroup studentGroupId;
}

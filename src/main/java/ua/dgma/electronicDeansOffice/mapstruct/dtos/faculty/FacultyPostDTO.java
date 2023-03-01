package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FacultyPostDTO {

    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String name;
}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FacultyPatchDTO {

    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String name;
}

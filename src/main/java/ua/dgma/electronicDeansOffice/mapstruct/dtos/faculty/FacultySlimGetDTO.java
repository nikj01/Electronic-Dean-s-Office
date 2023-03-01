package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FacultySlimGetDTO {

    @NotBlank
    private String name;
}

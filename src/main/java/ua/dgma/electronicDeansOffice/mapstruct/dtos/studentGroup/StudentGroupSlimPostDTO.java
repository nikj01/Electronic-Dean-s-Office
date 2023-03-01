package ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StudentGroupSlimPostDTO {

    @NotBlank(message = "The field |GROUP NAME| cannot be empty!")
    private String name;
}

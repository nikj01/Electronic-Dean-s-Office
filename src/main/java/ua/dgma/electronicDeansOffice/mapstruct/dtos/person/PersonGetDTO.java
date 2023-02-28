package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PersonGetDTO extends PersonSlimGetDTO {

    @NotBlank(message = "The field |PASSWORD| cannot be empty!")
    private String password;
}

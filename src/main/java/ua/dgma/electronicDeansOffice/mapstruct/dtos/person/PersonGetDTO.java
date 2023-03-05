package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
public class PersonGetDTO extends PersonSlimGetDTO {

    @NotNull
    private Long uid;
    @NotNull
    private Date dateOfBirth;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}

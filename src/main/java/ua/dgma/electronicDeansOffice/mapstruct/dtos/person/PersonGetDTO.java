package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class PersonGetDTO extends PersonSlimGetDTO {

    private Long uid;
    private Date dateOfBirth;
    private String email;
    private String password;
}

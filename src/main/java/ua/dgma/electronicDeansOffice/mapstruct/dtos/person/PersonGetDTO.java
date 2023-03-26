package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.Data;
import ua.dgma.electronicDeansOffice.models.PersonRoleEnum;

import java.sql.Date;

@Data
public class PersonGetDTO extends PersonSlimGetDTO {

    private Long uid;
    private Date dateOfBirth;
    private String email;
    private String password;
}

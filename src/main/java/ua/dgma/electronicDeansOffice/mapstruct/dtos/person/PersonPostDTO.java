package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.Data;
import ua.dgma.electronicDeansOffice.models.PersonRoleEnum;

import java.sql.Date;
import java.util.List;

@Data
public class PersonPostDTO {

    private Long uid;
    private String surname;
    private String name;
    private String patronymic;
    private Date dateOfBirth;
    private String email;
    private List<PersonRoleEnum> personRoles;
    private String password;
}

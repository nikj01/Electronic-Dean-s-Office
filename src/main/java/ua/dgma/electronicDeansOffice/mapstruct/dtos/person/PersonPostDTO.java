package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.models.PersonRole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class PersonPostDTO {

    private Long uid;
    private String surname;
    private String name;
    private String patronymic;
    private Date dateOfBirth;
    private String email;
    private PersonRole role;
    private String password;
}

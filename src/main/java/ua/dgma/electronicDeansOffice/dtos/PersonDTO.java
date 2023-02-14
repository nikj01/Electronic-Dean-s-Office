package ua.dgma.electronicDeansOffice.dtos;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.models.PersonRole;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Getter
@Setter
public class PersonDTO {

    @NotEmpty(message = "The field |UID| cannot be empty!")
    private Long uid;

    @NotBlank(message = "The field |SURNAME| cannot be empty!")
    private String surname;

    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String name;

    @NotBlank(message = "The field |PATRONYMIC| cannot be empty!")
    private String patronymic;

    @NotBlank(message = "The field |EMAIL| cannot be empty!")
    private String email;

    @NotEmpty(message = "The filed |ROLE| cannot be empty!")
    @Enumerated(EnumType.STRING)
    private PersonRole role;

    @NotEmpty(message = "The field |DATE OF BIRTH| cannot be empty!")
    private Date dateOfBirth;

}

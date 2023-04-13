package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.Data;
import ua.dgma.electronicDeansOffice.models.PersonRoleEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Data
public class PersonPatchDTO {
    @NotBlank(message = "The field |SURNAME| cannot be empty!")
    private String surname;
    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String name;
    @NotBlank(message = "The field |PATRONYMIC| cannot be empty!")
    private String patronymic;
    @NotNull(message = "The filed |ROLE| cannot be empty!")
    private List<PersonRoleEnum> personRoles;
    @NotNull(message = "The field |DATE OF BIRTH| cannot be empty!")
    private Date dateOfBirth;
    @NotBlank(message = "The field |EMAIL| cannot be empty!")
    private String email;
    @NotBlank(message = "The field |PASSWORD| cannot be empty!")
    private String password;
    private boolean deleted;
}

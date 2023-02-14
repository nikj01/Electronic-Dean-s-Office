package ua.dgma.electronicDeansOffice.dto;

import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;

public class PersonDTO {

    @NonNull
    @NotEmpty(message = "The field |UID| cannot be empty!")

    private Long uid;

    @NonNull
    @NotBlank(message = "The field |SURNAME| cannot be empty!")
    private String surname;

    @NonNull
    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String name;

    @NonNull
    @NotBlank(message = "The field |PATRONYMIC| cannot be empty!")
    private String patronymic;

    @NonNull
    @NotBlank(message = "The field |EMAIL| cannot be empty!")
    private String email;

    @NonNull
    @NotEmpty(message = "The field |DATE OF BIRTH| cannot be empty!")
    private Date dateOfBirth;
}

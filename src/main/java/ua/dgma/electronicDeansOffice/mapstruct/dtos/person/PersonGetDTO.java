package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.Data;
import ua.dgma.electronicDeansOffice.models.PersonRoleEnum;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PersonGetDTO {
    private Long uid;
    private String surname;
    private String name;
    private String patronymic;
    private List<PersonRoleEnum> personRoles;
    private Date dateOfBirth;
    private String email;
    private boolean deleted;
    private LocalDateTime wasDeleted;
}

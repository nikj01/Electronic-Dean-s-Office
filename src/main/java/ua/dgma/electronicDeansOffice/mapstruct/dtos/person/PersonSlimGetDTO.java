package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.Data;
import ua.dgma.electronicDeansOffice.models.PersonRoleEnum;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PersonSlimGetDTO {
    private Long uid;
    private String surname;
    private String name;
    private String patronymic;
    private List<PersonRoleEnum> personRoles;
    private boolean deleted;
    private LocalDateTime wasDeleted;
}

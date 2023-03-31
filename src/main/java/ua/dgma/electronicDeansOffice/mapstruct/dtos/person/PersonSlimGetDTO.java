package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.models.PersonRoleEnum;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class PersonSlimGetDTO {

    private Long uid;
    private String surname;
    private String name;
    private String patronymic;
    private boolean deleted;

}

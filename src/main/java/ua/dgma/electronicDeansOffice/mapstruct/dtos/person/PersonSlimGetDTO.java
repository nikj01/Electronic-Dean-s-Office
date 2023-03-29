package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.models.PersonRoleEnum;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class PersonSlimGetDTO implements Comparable<PersonSlimGetDTO>{

    private Long uid;
    private String surname;
    private String name;
    private String patronymic;
    private List<PersonRoleEnum> personRoles;
    private boolean deleted;

    @Override
    public int compareTo(PersonSlimGetDTO o) {
        if(this.surname.compareToIgnoreCase(o.getSurname()) > 0) {
            return 1;
        } else if(this.surname.compareToIgnoreCase(o.getSurname()) < 0) {
            return -1;
        } else {
            return 0;
        }
    }

}

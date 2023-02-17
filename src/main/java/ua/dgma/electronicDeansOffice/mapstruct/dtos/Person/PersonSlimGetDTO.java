package ua.dgma.electronicDeansOffice.mapstruct.dtos.Person;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.models.PersonRole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
public class PersonSlimGetDTO implements Comparable<PersonSlimGetDTO>{

    @NotNull(message = "The field |UID| cannot be empty!")
    private Long uid;

    @NotBlank(message = "The field |SURNAME| cannot be empty!")
    private String surname;

    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String name;

    @NotBlank(message = "The field |PATRONYMIC| cannot be empty!")
    private String patronymic;

    @NotNull(message = "The field |DATE OF BIRTH| cannot be empty!")
    private Date dateOfBirth;

    @NotBlank(message = "The field |EMAIL| cannot be empty!")
    private String email;

    @NotNull(message = "The filed |ROLE| cannot be empty!")
    private PersonRole role;

    @Override
    public int compareTo(PersonSlimGetDTO o) {
        if(this.uid > o.getUid()){
            return 1;
        } else if(this.uid < o.getUid()){
            return -1;
        } else {
            return 0;
        }
    }
}

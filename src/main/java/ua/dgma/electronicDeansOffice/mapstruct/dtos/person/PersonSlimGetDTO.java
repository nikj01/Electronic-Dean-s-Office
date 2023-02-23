package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PersonSlimGetDTO implements Comparable<PersonSlimGetDTO>{

    @NotBlank
    private String surname;

    @NotBlank
    private String name;

    @NotBlank
    private String patronymic;

    @Override
    public int compareTo(PersonSlimGetDTO o) {
        if(this.surname.compareToIgnoreCase(o.getSurname()) > 0){
            return 1;
        } else if(this.surname.compareToIgnoreCase(o.getSurname()) < 0){
            return -1;
        } else {
            return 0;
        }
    }

}

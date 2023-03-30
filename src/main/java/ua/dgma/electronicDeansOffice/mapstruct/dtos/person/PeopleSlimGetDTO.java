package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.*;

import java.util.List;

@Data
public class PeopleSlimGetDTO {

    private List<PersonSlimGetDTO> people;
}

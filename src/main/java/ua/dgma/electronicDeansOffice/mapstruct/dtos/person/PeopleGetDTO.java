package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.*;

import java.util.List;

@Data
public class PeopleGetDTO {

    private List<PersonGetDTO> people;
}

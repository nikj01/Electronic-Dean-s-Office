package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PeopleSlimGetDTO {

    @NonNull
    private List<PersonSlimGetDTO> people;
}

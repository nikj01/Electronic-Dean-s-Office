package ua.dgma.electronicDeansOffice.mapstruct.dtos.Person;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PersonsResponseDTO {

    @NonNull
    private List<PersonDTO> persons;
}

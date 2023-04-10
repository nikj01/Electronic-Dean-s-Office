package ua.dgma.electronicDeansOffice.utill.validators.data;

import lombok.*;
import org.springframework.validation.Errors;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;

@Getter
@Setter
@RequiredArgsConstructor
public class PersonValidationData {
    @NonNull
    private Person person;
    @NonNull
    private PeopleRepository peopleRepository;
    @NonNull
    private Errors errors;
}

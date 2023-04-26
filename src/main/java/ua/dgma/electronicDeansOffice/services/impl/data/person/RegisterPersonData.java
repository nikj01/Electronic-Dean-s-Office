package ua.dgma.electronicDeansOffice.services.impl.data.person;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Person;

@Data
@RequiredArgsConstructor
public class RegisterPersonData<P extends Person> {
    @NonNull
    private P newPerson;
    @NonNull
    private BindingResult bindingResult;

    public RegisterPersonData(@NonNull P newPerson) {
        this.newPerson = newPerson;
    }
}

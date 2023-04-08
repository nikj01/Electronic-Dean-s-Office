package ua.dgma.electronicDeansOffice.services.impl.data.person;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Person;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UpdatePersonData<P extends Person> {
    @NonNull
    private Long uid;
    @NonNull
    private P updatedPerson;
    @NonNull
    private BindingResult bindingResult;

    public UpdatePersonData(@NonNull Long uid,
                            @NonNull P updatedPerson) {
        this.uid = uid;
        this.updatedPerson = updatedPerson;
    }
}

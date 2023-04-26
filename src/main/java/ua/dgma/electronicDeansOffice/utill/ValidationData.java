package ua.dgma.electronicDeansOffice.utill;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Component
public class ValidationData<T> {
    @NonNull
    private Validator validator;
    @NonNull
    private T objectToBeValidated;
    @NonNull
    private BindingResult bindingResult;

}

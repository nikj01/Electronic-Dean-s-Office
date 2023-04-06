package ua.dgma.electronicDeansOffice.utill.validators.data;

import lombok.*;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.utill.validators.AbstractValidator;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Component
public class DataForAbstractValidator<T> {
    @NonNull
    private AbstractValidator validator;
    @NonNull
    private T objectToBeValidated;
}

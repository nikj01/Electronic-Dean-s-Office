package ua.dgma.electronicDeansOffice.exceptions;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Component
public class ExceptionData<T> {

    @NonNull
    private String copyOfClass;

    @NonNull
    private String nameOfParam;

    @NonNull
    private T param;
}

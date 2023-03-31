package ua.dgma.electronicDeansOffice.utill.check.data;

import lombok.*;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.repositories.functional.ExistByNameInterface;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Component
public class CheckExistsByNameData<R extends ExistByNameInterface> {
    @NonNull
    private String className;
    @NonNull
    private String name;
    @NonNull
    private R repository;
}

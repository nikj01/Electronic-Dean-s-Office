package ua.dgma.electronicDeansOffice.utill.check.data;

import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Component
public class CheckExistsByIdData<T> {

    @NonNull
    private String className;
    @NonNull
    private T id;
    @NonNull
    private JpaRepository repository;

}

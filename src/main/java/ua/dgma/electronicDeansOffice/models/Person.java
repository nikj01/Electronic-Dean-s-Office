package ua.dgma.electronicDeansOffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
@Data
@NoArgsConstructor
public class Person {

    @NonNull
    @NotBlank(message = "Surname may not be blank")
    @Column(nullable = false)
    protected String surname;

    @NonNull
    @NotBlank(message = "may not be blank")
    @Column(nullable = false)
    protected String name;

    @NonNull
    @NotBlank(message = "Patronymic may not be blank")
    @Column(nullable = false)
    protected String patronymic;

}

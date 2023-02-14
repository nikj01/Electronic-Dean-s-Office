package ua.dgma.electronicDeansOffice.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;


@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Table(name = "Persons")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    @Id
    @NonNull
    @NotEmpty(message = "The field |UID| cannot be empty!")
    @Column(
            nullable = false,
            unique = true
    )
    private Long uid;

    @NonNull
    @NotBlank(message = "The field |SURNAME| cannot be empty!")
    @Column(nullable = false)
    private String surname;

    @NonNull
    @NotBlank(message = "The field |NAME| cannot be empty!")
    @Column(nullable = false)
    private String name;

    @NonNull
    @NotBlank(message = "The field |PATRONYMIC| cannot be empty!")
    @Column(nullable = false)
    private String patronymic;

    @NonNull
    @NotBlank(message = "The field |EMAIL| cannot be empty!")
    private String email;

    @NonNull
    @NotEmpty(message = "The filed |ROLE| cannot be empty!")
    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
    private PersonRole role;

    @NonNull
    @NotEmpty(message = "The field |DATE OF BIRTH| cannot be empty!")
    @Column(nullable = false)
    private Date dateOfBirth;

}

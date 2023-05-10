package ua.dgma.electronicDeansOffice.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "People", indexes = {
        @Index(columnList = "surname DESC", name="peopleSurnameIndex")})
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Comparable<Person> {
    @Id
    @NotNull(message = "The field |UID| cannot be empty!")
    @Column(
            nullable = false,
            unique = true)
    private Long uid;

    @NotBlank(message = "The field |SURNAME| cannot be empty!")
    @Column(nullable = false)
    private String surname;

    @NotBlank(message = "The field |NAME| cannot be empty!")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "The field |PATRONYMIC| cannot be empty!")
    @Column(nullable = false)
    private String patronymic;

    @NotBlank(message = "The field |EMAIL| cannot be empty!")
    @Column(unique = true)
    private String email;

    @NotNull(message = "The filed |ROLE| cannot be empty!")
    @ElementCollection(targetClass = PersonRoleEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "personRoles")
    @Column(name = "roles", nullable = false)
    private Set<PersonRoleEnum> personRoles = new HashSet<>();

    @NotBlank(message = "The field |PASSWORD| cannot be empty!")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "The field |DATE OF BIRTH| cannot be empty!")
    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private boolean deleted;

    private LocalDateTime wasDeleted;

    @Override
    public int compareTo(Person o) {
        return uid.compareTo(o.getUid());
    }
}

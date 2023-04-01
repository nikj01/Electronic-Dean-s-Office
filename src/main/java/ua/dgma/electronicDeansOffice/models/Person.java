package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "People", indexes = {
        @Index(columnList = "surname DESC", name="peopleSurnameIndex")
})
@Inheritance(strategy = InheritanceType.JOINED)
//@Where(clause = "DELETED = false")
public class Person {

    @Id
    @NotNull(message = "The field |UID| cannot be empty!")
    @Column(
            nullable = false,
            unique = true
    )
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
    @ElementCollection(targetClass = PersonRoleEnum.class)
    @CollectionTable(name = "personRoles")
    @Column(name = "roles", nullable = false)
    private List<PersonRoleEnum> personRoles;

    @NotBlank(message = "The field |PASSWORD| cannot be empty!")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "The field |DATE OF BIRTH| cannot be empty!")
    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private boolean deleted;
}

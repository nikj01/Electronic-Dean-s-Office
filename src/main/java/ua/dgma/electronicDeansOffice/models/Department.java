package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "studentGroups")
@Table(name = "Departments")
public class Department implements Serializable {

    @Id
    @NotBlank(message = "The field |NAME| cannot be empty!")
    @Column(
            nullable = false,
            unique = true
    )
    private String name;

    @NotEmpty(message = "The field |FACULTY| cannot be empty!")
    @ManyToOne
    @Cascade(value = org.hibernate.annotations.CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    private Faculty faculty;

    @OneToMany(
            mappedBy = "department",
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SELECT)
//    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Set<StudentGroup> studentGroups = new HashSet<>();

    @OneToMany(
            mappedBy = "department",
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SELECT)
    private Set<Teacher> teachers = new HashSet<>();
}

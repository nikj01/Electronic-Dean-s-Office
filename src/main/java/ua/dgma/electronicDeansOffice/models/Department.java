package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "studentGroups")
@Table(name = "Departments", indexes = {
        @Index(columnList = "name DESC", name = "departmentNameIndex")
})
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The field |NAME| cannot be empty!")
    @Column(
            nullable = false,
            unique = true
    )
    private String name;

    @NotNull(message = "The field |FACULTY| cannot be empty!")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    private Faculty faculty;

    @OneToMany(
            mappedBy = "department",
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    private Set<StudentGroup> studentGroups = new HashSet<>();

    @OneToMany(
            mappedBy = "department",
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SELECT)
    private List<Teacher> teachers = new ArrayList<>();

    @NonNull
    @Column(nullable = false)
    private boolean deleted;
}

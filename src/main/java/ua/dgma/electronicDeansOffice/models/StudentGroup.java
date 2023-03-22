package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"students", "events"})
@Table(name = "StundentGroups")
//@EqualsAndHashCode(exclude = "department")
public class StudentGroup implements Serializable {

    @Id
    @NonNull
    @NotBlank(message = "The field |NAME| cannot be empty!")
    @Column(
            unique = true,
            nullable = false
    )
    private String name;

    @NotEmpty(message = "The field |GROUP LEADER| cannot be empty!")
    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(
            unique = true,
            referencedColumnName = "uid"
    )
    private Student groupLeader;

    @OneToMany(
            mappedBy = "studentGroup",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Set<Student> students = new TreeSet<>();

    @NotEmpty(message = "The field |CURATOR| cannot be empty!")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Teacher curator;


    @NotEmpty(message = "The field |DEPARTMENT| cannot be empty!")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    private Department department;

    @ManyToMany(
            mappedBy = "studentGroups",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Event> events;

}

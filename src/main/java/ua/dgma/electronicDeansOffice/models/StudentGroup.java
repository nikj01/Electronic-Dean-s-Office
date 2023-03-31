package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.mapping.AccessOptions;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"students", "events"})
@Table(name = "StundentGroups", indexes = {
        @Index(columnList = "name DESC", name = "studentGroupNameIndex")
})
public class StudentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "The field |NAME| cannot be empty!")
    @Column(
            unique = true,
            nullable = false
    )
    private String name;

//    @NotNull(message = "The field |GROUP LEADER| cannot be empty!")
    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            unique = true,
            referencedColumnName = "uid"
    )
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Student groupLeader;

    @OneToMany(
            mappedBy = "studentGroup",
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SELECT)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<Student> students = new ArrayList<>();

/*
*   This annotation @NotEmpty must be working after creating TeacherValidator!
* */
    @NotNull(message = "The field |CURATOR| cannot be empty!")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Teacher curator;

    @NotNull(message = "The field |DEPARTMENT| cannot be empty!")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    private Department department;

    @ManyToMany(
            mappedBy = "studentGroups",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<Event> events;

    @NonNull
    @Column(nullable = false)
    private boolean deleted;
}

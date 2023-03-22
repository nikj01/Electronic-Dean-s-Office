package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"studentGroup", "department"})
@Table(name = "Teachers")
public class Teacher extends Person {

    @NotEmpty(message = "The field |DEPARTMENT| cannot be empty!")
    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            nullable = false,
            referencedColumnName = "name"
    )
    private Department department;

    @OneToMany(
            mappedBy = "curator",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private Set<StudentGroup> studentGroup = new HashSet<>();

//    @NonNull
//    @NotEmpty(message = "The field |TEACHER'S JOURNAL| cannot be empty!")
//    @OneToOne(mappedBy = "teacher")
//    private TeachersJournal journal;
}

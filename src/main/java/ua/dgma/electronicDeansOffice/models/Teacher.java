package ua.dgma.electronicDeansOffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "Teachers")
public class Teacher extends Person {

    @NonNull
    @NotEmpty(message = "The field |DEPARTMENT| cannot be empty!")
    @OneToOne
    @JoinColumn(
            nullable = false,
            referencedColumnName = "name"
    )
    private Department department;

    @OneToMany(mappedBy = "curator")
    private Set<StudentGroup> studentGroup = new HashSet<>();

    @NonNull
    @NotEmpty(message = "The field |TEACHER'S JOURNAL| cannot be empty!")
    @OneToOne(mappedBy = "teacher")
    private TeachersJournal journal;
}

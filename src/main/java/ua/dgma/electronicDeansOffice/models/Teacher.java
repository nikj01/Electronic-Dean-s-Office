package ua.dgma.electronicDeansOffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Teacher")
@Data
@NoArgsConstructor
public class Teacher extends Person {

    @Id
    @NonNull
    @NotEmpty(message = "Staff number cannot be empty")
    @Min(
            value = 0,
            message = "Staff number number cannot be less than 0 characters"
    )
    @Column(nullable = false)
    private Long staffNumber;

    @NonNull
    @OneToMany(mappedBy = "curator")
    private Set<StudentGroup> studentGroup;

    @NonNull
    @OneToMany(mappedBy = "teacher")
    private List<Grade> grades;
}

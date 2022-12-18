package ua.dgma.electronicDeansOffice.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "Student")
@Data
@NoArgsConstructor
public class Student extends Person {

    @Id
    @NotEmpty(message = "Gradebook cannot be empty")
    @Min(
            value = 8,
            message = "Gradebook number cannot be less than 8 characters"
    )
    @Column(nullable = false)
    private Long gradeBook;

    @NonNull
    @NotEmpty
    @ManyToOne
    @JoinColumn(
            name = "group_id",
            referencedColumnName = "id",
            nullable = false
    )
    private StudentGroup studentGroupId;

    @OneToMany(mappedBy = "student")
    private List<Grade> gradeSet;
}

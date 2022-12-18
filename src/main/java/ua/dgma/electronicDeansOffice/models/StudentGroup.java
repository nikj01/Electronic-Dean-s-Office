package ua.dgma.electronicDeansOffice.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "StudentGroup")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class StudentGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "Group may not be blank")
    @Size(
            min = 1,
            max = 255
    )
    @Column(
            unique = true,
            nullable = false
    )
    private String name;

    @NonNull
    @NotNull(message = "Student list cannot be empty")
    @OneToMany(
            mappedBy = "studentGroupId",
            cascade = CascadeType.ALL
    )
    private List<Student> studentList;

    @NonNull
    @NotEmpty(message = "Curator staff name cannot be empty")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Teacher curator;
}

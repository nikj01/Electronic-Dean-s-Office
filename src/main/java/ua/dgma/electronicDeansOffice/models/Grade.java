package ua.dgma.electronicDeansOffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "Grades")
@Data
@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty(message = "Subject cannot be empty")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Subject subject;

    @NonNull
    @NotEmpty(message = "Teacher cannot be empty")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Teacher teacher;

    @NonNull
    @NotEmpty(message = "Grade cannot be empty")
    @Min(
            value = 0,
            message = "Grade should be greater than 0"
    )
    @Max(
            value = 100,
            message = "Grade cannot be greater than 100"
    )
    @Column(nullable = false)
    private int grade;

    @NonNull
    @NotEmpty(message = "Student cannot be empty")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Student student;

    @NonNull
    @NotEmpty(message = "Date cannot be empty")
    private LocalDateTime date;

}

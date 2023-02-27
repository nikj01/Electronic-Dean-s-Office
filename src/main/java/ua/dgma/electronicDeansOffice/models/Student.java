package ua.dgma.electronicDeansOffice.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "Students")
public class Student extends Person {

    @NonNull
    @NotNull(message = "The field |STUDENT GROUP| group cannot be empty!")
    @ManyToOne
    @JoinColumn(
            name = "group_name",
            referencedColumnName = "name",
            nullable = false
    )
    private StudentGroup studentGroup;

}

package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Optional;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "studentGroup")
@Table(name = "Students")
public class Student extends Person {

    @NotNull(message = "The field |STUDENT GROUP| group cannot be empty!")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            name = "group_id",
            referencedColumnName = "id",
            nullable = false
    )
    private StudentGroup studentGroup;

}

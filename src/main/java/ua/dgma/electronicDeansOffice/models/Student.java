package ua.dgma.electronicDeansOffice.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;



@Entity
@Data
@NoArgsConstructor
@Table(name = "Students")
public class Student extends Person {

    @NonNull
    @NotEmpty(message = "The field |STUDENT GROUP| group cannot be empty!")
    @ManyToOne
    @JoinColumn(
            name = "group_name",
            referencedColumnName = "name",
            nullable = false
    )
    private StudentGroup studentGroupId;

}

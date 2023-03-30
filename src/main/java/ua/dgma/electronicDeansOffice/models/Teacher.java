package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
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
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            nullable = false,
            referencedColumnName = "name"
    )
    private Department department;

    @OneToMany(
            mappedBy = "curator",
            fetch = FetchType.EAGER
    )
    @Fetch(FetchMode.SELECT)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Set<StudentGroup> studentGroup = new HashSet<>();

}

package ua.dgma.electronicDeansOffice.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"studentGroup", "department"})
@Table(name = "Teachers")
public class Teacher extends Person {
    @NotNull(message = "The field |DEPARTMENT| cannot be empty!")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            nullable = false,
            referencedColumnName = "id"
    )
    private Department department;

    @OneToMany(
            mappedBy = "curator",
            fetch = FetchType.LAZY
    )
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private List<StudentGroup> studentGroups = new ArrayList<>();

}

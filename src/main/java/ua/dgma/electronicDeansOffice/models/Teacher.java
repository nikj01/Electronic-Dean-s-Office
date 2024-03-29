package ua.dgma.electronicDeansOffice.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            nullable = false,
            referencedColumnName = "id")
    private Department department;

    @OneToMany(
            mappedBy = "curator",
            fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @LazyCollection(value = LazyCollectionOption.TRUE)
    @Where(clause = "deleted != true")
    private List<StudentGroup> studentGroups = new ArrayList<>();

}

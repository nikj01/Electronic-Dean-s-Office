package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "DeaneryWorkers")
public class DeaneryWorker extends Person {

    @NotNull(message = "The field |FACULTY| cannot be empty!")
    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
            )
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Faculty faculty;

}

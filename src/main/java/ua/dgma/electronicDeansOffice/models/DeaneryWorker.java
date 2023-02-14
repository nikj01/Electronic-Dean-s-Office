package ua.dgma.electronicDeansOffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@Table(name = "DeaneryWorkers")
public class DeaneryWorker extends Person {

    @NonNull
    @NotEmpty(message = "The field |FACULTY| cannot be empty!")
    @OneToOne
    private Faculty faculty;


}

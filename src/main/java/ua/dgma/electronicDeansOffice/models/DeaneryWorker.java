package ua.dgma.electronicDeansOffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "DeaneryWorkers")
public class DeaneryWorker extends Person {

    @NonNull
    @NotNull(message = "The field |FACULTY| cannot be empty!")
    @OneToOne
    private Faculty faculty;

}

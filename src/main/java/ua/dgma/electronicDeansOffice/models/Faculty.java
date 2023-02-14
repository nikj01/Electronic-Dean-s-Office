package ua.dgma.electronicDeansOffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Faculties")
@ToString
public class Faculty implements Serializable {

    @Id
    @NonNull
    @NotBlank(message = "The field |NAME| cannot be empty!")
    @Column(
            nullable = false,
            unique = true
    )
    private String name;

    @OneToMany(mappedBy = "faculty")
    private Set<Department> departments;
}

package ua.dgma.electronicDeansOffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "Departments")
public class Department implements Serializable {

    @Id
    @NonNull
    @NotBlank(message = "The field |NAME| cannot be empty!")
    @Column(
            nullable = false,
            unique = true
    )
    private String name;

    @NonNull
    @NotEmpty(message = "The field |FACULTY| cannot be empty!")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Faculty faculty;

    @OneToMany(mappedBy = "department")
    private Set<StudentGroup> studentGroups = new HashSet<>();
}

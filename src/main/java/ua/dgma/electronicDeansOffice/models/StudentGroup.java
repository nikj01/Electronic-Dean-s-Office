package ua.dgma.electronicDeansOffice.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "StundentGroups")
public class StudentGroup implements Serializable {

    @Id
    @NonNull
    @NotBlank(message = "The field |NAME| cannot be empty!")
    @Size(
            min = 1,
            max = 255
    )
    @Column(
            unique = true,
            nullable = false
    )
    private String name;

    @NonNull
    @NotEmpty(message = "The field |GROUP LEADER| cannot be empty!")
    @OneToOne
    @JoinColumn(
            unique = true,
            referencedColumnName = "uid"
    )
    private Student groupLeader;

    /*
    *   SET OR LIST???
    */
    @OneToMany(mappedBy = "studentGroup")
    private Set<Student> students = new TreeSet<>();

    @NonNull
    @NotEmpty(message = "The field |CURATOR| cannot be empty!")
    @ManyToOne
    @JoinColumn //  Idk can be a group without a curator
    private Teacher curator;


    @NonNull
    @NotEmpty(message = "The field |DEPARTMENT| cannot be empty!")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Department department;

}

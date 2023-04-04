package ua.dgma.electronicDeansOffice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"departments", "deaneryWorkers"})
@Table(name = "Faculties", indexes = {
        @Index(columnList = "name DESC", name = "facultyNameIndex")
})
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The field |NAME| cannot be empty!")
    @Column(
            nullable = false,
            unique = true
    )
    private String name;

    @OneToMany(
            mappedBy = "faculty",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Department> departments = new ArrayList<>();

    @OneToMany(
            mappedBy = "faculty",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<DeaneryWorker> deaneryWorkers = new ArrayList<>();

    @Column(nullable = false)
    private boolean deleted;
}

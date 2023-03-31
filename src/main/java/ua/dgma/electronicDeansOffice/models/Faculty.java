package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
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
            fetch = FetchType.EAGER,
            cascade = javax.persistence.CascadeType.MERGE,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    private List<Department> departments = new ArrayList<>();

    @OneToMany(
            mappedBy = "faculty",
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    private List<DeaneryWorker> deaneryWorkers = new ArrayList<>();

    @NonNull
    @Column(nullable = false)
    private boolean deleted;
}

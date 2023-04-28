package ua.dgma.electronicDeansOffice.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
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
        @Index(columnList = "name DESC", name = "facultyNameIndex")})
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facultySeq")
    @SequenceGenerator(name = "facultySeq", sequenceName = "facultySeqq", initialValue = 6)
    private Long id;

    @NotBlank(message = "The field |NAME| cannot be empty!")
    @Column(
            nullable = false,
            unique = true)
    private String name;

    @OneToMany(
            mappedBy = "faculty",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private List<Department> departments = new ArrayList<>();

    @OneToMany(
            mappedBy = "faculty",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private List<DeaneryWorker> deaneryWorkers = new ArrayList<>();

    @Column(nullable = false)
    private boolean deleted;
}

package ua.dgma.electronicDeansOffice.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "studentGroups")
@Table(name = "Departments", indexes = {
        @Index(columnList = "name DESC", name = "departmentNameIndex")})
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "depSeq")
    @SequenceGenerator(name = "depSeq", sequenceName = "depSeqq", initialValue = 21)
    private Long id;

    @NotBlank(message = "The field |NAME| cannot be empty!")
    @Column(
            nullable = false,
            unique = true)
    private String name;

    @NotNull(message = "The field |FACULTY| cannot be empty!")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    private Faculty faculty;

    @OneToMany(
            mappedBy = "department",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @LazyCollection(value = LazyCollectionOption.TRUE)
    @Where(clause = "deleted != true")
    private List<StudentGroup> studentGroups = new ArrayList<>();

    @OneToMany(
            mappedBy = "department",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @LazyCollection(value = LazyCollectionOption.TRUE)
    @Where(clause = "deleted != true")
    private List<Teacher> teachers = new ArrayList<>();

    @Column(nullable = false)
    private boolean deleted;

    private LocalDateTime wasDeleted;
}

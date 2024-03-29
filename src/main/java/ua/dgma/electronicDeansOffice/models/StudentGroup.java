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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"students", "events"})
@Table(name = "StundentGroups", indexes = {
        @Index(columnList = "name DESC", name = "studentGroupNameIndex")})
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groupSeq")
    @SequenceGenerator(name = "groupSeq", sequenceName = "groupSeqq", initialValue = 11)
    private Long id;

    @NotBlank(message = "The field |NAME| cannot be empty!")
    @Column(
            unique = true,
            nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            unique = true,
            referencedColumnName = "uid")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Student groupLeader;

    @OneToMany(
            mappedBy = "studentGroup",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @LazyCollection(value = LazyCollectionOption.TRUE)
    @Where(clause = "deleted != true")
    private List<Student> students = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn
    private Teacher curator;

    @NotNull(message = "The field |DEPARTMENT| cannot be empty!")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    private Department department;

    @ManyToMany(
            mappedBy = "studentGroups",
            fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private Set<JournalPage> pages = new HashSet<>();

    @ManyToMany(
            mappedBy = "studentGroups",
            fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private List<Event> events = new ArrayList<>();

    @OneToMany(
            mappedBy = "studentGroup",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private List<Report> reports = new ArrayList<>();

    @Column(nullable = false)
    private boolean deleted;

    private LocalDateTime wasDeleted;
}

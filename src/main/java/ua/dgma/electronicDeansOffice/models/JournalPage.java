package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "JournalsPages")
public class JournalPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The field |PAGE NAME| cannot be empty!")
    @Column(nullable = false)
    private String pageName;

    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SELECT)
//    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Set<StudentGroup> studentGroups = new HashSet<>();

    @OneToMany(
            mappedBy = "page",
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private Set<Event> events = new HashSet<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private Set<Report> reports = new HashSet<>();

    @NotEmpty
    @ManyToOne
    @Cascade(value = org.hibernate.annotations.CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    private TeachersJournal journal;
}

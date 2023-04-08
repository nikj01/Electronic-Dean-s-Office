package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
            fetch = FetchType.LAZY
    )
    @Fetch(value = FetchMode.SELECT)
//    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<StudentGroup> studentGroups = new ArrayList<>();

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    private TeachersJournal journal;
}

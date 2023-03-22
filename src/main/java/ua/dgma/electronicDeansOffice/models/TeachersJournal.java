package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"teacher", "pages"})
@Table(name = "TeachersJournals")
public class TeachersJournal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The field |TEACHER| cannot be empty!")
    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Teacher teacher;

    @OneToMany(
            mappedBy = "journal",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Set<JournalPage> pages = new TreeSet<>();

}

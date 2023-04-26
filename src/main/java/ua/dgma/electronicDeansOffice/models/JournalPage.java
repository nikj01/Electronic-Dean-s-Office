package ua.dgma.electronicDeansOffice.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"events", "studentGroups", "reports"})
@Table(name = "JournalsPages")
public class JournalPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The field |PAGE NAME| cannot be empty!")
    @Column(nullable = false)
    private String pageName;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<StudentGroup> studentGroups = new HashSet<>();

    @OneToMany(
            mappedBy = "page",
            fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    private List<Event> events = new ArrayList<>();

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    private TeachersJournal journal;

    @Column(nullable = false)
    private boolean archive;
}

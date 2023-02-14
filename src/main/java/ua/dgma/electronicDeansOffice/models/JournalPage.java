package ua.dgma.electronicDeansOffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "JournalsPages")
public class JournalPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "The field |PAGE NAME| cannot be empty!")
    @Column(nullable = false)
    private String pageName;

    @OneToMany
    private Set<StudentGroup> studentGroups = new HashSet<>();

    @OneToMany(mappedBy = "page")
    private Set<Event> events = new HashSet<>();

    @OneToMany
    private Set<Report> reports = new HashSet<>();

    @NonNull
    @NotEmpty
    @ManyToOne
    @JoinColumn(nullable = false)
    private TeachersJournal journal;
}

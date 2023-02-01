package ua.dgma.electronicDeansOffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "TeachersJournals")
public class TeachersJournal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty(message = "The field |TEACHER| cannot be empty!")
    @OneToOne
    @JoinColumn(nullable = false)
    private Teacher teacher;

    @OneToMany(mappedBy = "journal")
    private Set<JournalPage> pages;

}

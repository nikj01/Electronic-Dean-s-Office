package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.*;

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

    @NonNull
    @NotEmpty(message = "The field |TEACHER| cannot be empty!")
    @OneToOne(
            orphanRemoval = true,
            fetch = FetchType.LAZY)
//    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Teacher teacher;

    @OneToMany(
            mappedBy = "journal",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    private List<JournalPage> pages = new ArrayList<>();

}

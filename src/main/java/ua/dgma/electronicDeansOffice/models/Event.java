package ua.dgma.electronicDeansOffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "The field |EVENT THEME| cannot be empty!")
    @Column(nullable = false)
    private String eventTheme;

    /*
    *   CHECK THE ANNOTATION "@NOT_BLANK" AND "NULLABLE"!
    */
    @NonNull
    @NotBlank
    private String description;

    @NonNull
    @NotEmpty(message = "The field |EVENT TYPE| cannot be empty!")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;


    /*
    *   CHECK WHAT DOES MEAN "@REFERENCE"!
    */
    @OneToMany
    @Reference
    private List<StudentGroup> studentGroups;


    @NonNull
    @NotEmpty(message = "The field |DATE| cannot be empty!")
    @Column(nullable = false)
    private LocalDate date;

    @NonNull
    @NotEmpty(message = "The field |JOURNAL PAGE| cannot be empty!")
    @ManyToOne
    @JoinColumn(
            name = "page_id",
            referencedColumnName = "id",
            nullable = false
    )
    private JournalPage page;

    @OneToOne(mappedBy = "event")
    private Report report;
}

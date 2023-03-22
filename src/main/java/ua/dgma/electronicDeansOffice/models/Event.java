package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "studentGroups")
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
    @ManyToMany
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Reference
    private List<StudentGroup> studentGroups;


    @NonNull
    @NotEmpty(message = "The field |DATE| cannot be empty!")
    @Column(nullable = false)
    private LocalDate date;

    @NonNull
    @NotEmpty(message = "The field |JOURNAL PAGE| cannot be empty!")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            name = "page_id",
            referencedColumnName = "id",
            nullable = false
    )
    private JournalPage page;

    @OneToOne(
            mappedBy = "event",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Report report;
}

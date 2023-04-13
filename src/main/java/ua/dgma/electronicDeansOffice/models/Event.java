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
@EqualsAndHashCode(exclude = "studentGroups")
@Table(name = "Events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The field |EVENT THEME| cannot be empty!")
    @Column(nullable = false)
    private String eventTheme;

    @NotBlank
    private String description;

    @NotEmpty(message = "The field |EVENT TYPE| cannot be empty!")
    @Column(nullable = false)
    private EventType eventType;

    @ManyToMany
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Reference
    private List<StudentGroup> studentGroups;

    @NotEmpty(message = "The field |DATE| cannot be empty!")
    @Column(nullable = false)
    private LocalDate date;

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

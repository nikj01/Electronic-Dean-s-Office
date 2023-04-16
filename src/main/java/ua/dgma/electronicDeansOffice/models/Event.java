package ua.dgma.electronicDeansOffice.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @NotNull(message = "The field |EVENT TYPE| cannot be empty!")
    @Column(nullable = false)
    private EventType eventType;

    @ManyToMany
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Reference
    private List<StudentGroup> studentGroups = new ArrayList<>();

    @NotNull(message = "The field |DATE| cannot be empty!")
    @Column(nullable = false)
    private LocalDate date;

    @NotNull(message = "The field |JOURNAL PAGE| cannot be empty!")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            name = "page_id",
            referencedColumnName = "id",
            nullable = false
    )
    private JournalPage page;

    @OneToOne(
            mappedBy = "event"
    )
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Report report;
}

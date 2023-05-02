package ua.dgma.electronicDeansOffice.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventSeq")
    @SequenceGenerator(name = "eventSeq", sequenceName = "eventSeqq", initialValue = 109)
    private Long id;

    @NotNull(message = "The field |SEMESTER| cannot be empty!")
    @Column(nullable = false)
    private Integer semester;

    @NotBlank(message = "The field |EVENT THEME| cannot be empty!")
    @Column(nullable = false)
    private String eventTheme;

    @NotBlank(message = "The field |DESCRIPTION| cannot be empty!")
    private String description;

    @NotNull(message = "The field |EVENT TYPE| cannot be empty!")
    @Column(nullable = false)
    private EventTypeEnum eventType;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private List<StudentGroup> studentGroups = new ArrayList<>();

    @NotNull(message = "The field |DATE| cannot be empty!")
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime date;

    @NotNull(message = "The field |JOURNAL PAGE| cannot be empty!")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            name = "page_id",
            referencedColumnName = "id",
            nullable = false)
    private JournalPage page;
}

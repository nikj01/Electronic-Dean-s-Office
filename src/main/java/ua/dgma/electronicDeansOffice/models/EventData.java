package ua.dgma.electronicDeansOffice.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "EventData")
public class EventData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edSeq")
    @SequenceGenerator(name = "edSeq", sequenceName = "edSeqq", initialValue = 16)
    private Long id;

    @NotNull(message = "The field |SEMESTER| cannot be empty!")
    @Column(nullable = false)
    private Integer semester;

    @NotNull(message = "The field |EVENT ID| cannot be empty!")
    @Column(nullable = false)
    private Long eventId;

    @NotNull(message = "The field |PAGE ID| cannot be empty!")
    @Column(nullable = false)
    private Long pageId;

    @NotBlank(message = "The field |PAGE NAME| cannot be empty!")
    @Column(nullable = false)
    private String pageName;

    @NotBlank(message = "The field |EVENT THEME| cannot be empty!")
    @Column(nullable = false)
    private String eventTheme;

    @NotBlank(message = "The field |DESCRIPTION| cannot be empty!")
    @Column(nullable = false)
    private String eventDescription;

    @NotNull(message = "The field |EVENT TYPE| cannot be empty!")
    @Column(nullable = false)
    private EventTypeEnum eventType;

    @NotNull(message = "The field |DATE| cannot be empty!")
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull(message = "The field |TEACHER| cannot be empty!")
    @Column(nullable = false)
    private Long teachersUid;

    @NotNull(message = "The field |TEACHER`S DATA| cannot be empty!")
    @Column(nullable = false)
    private String teachersData;

    @OneToMany(
            mappedBy = "eventData",
            fetch = FetchType.LAZY
    )
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private List<Report> report = new ArrayList<>();
}

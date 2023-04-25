package ua.dgma.electronicDeansOffice.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "EventData")
public class EventData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The field |SEMESTER| cannot be empty!")
    @Column(nullable = false)
    private Integer semester;

    @NotNull(message = "The field |EVENT ID| cannot be empty!")
    @Column(nullable = false)
    private Long eventId;

    @NotBlank(message = "The field |DESCRIPTION| cannot be empty!")
    @Column(nullable = false)
    private String pageName;

    @NotBlank(message = "The field |DESCRIPTION| cannot be empty!")
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
    @OneToOne
    private Teacher teacher;
}

package ua.dgma.electronicDeansOffice.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "Reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reportSeq")
    @SequenceGenerator(name = "reportSeq", sequenceName = "reportSeqq", initialValue = 1)
    private Long id;

    @NotBlank(message = "The field |REPORT NAME| cannot be empty!")
    @Column(nullable = false)
    private String reportName;

    @NotNull(message = "The field |EVENT DATA| cannot be empty!")
    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EventData eventData;

    @NotNull(message = "The field |STUDENT GROUP| cannot be empty!")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    private StudentGroup studentGroup;

    @NotNull(message = "The field |CREATED| cannot be empty!")
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime created;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updated;

    @ElementCollection
    @CollectionTable(
            name = "student_attendance",
            joinColumns = @JoinColumn(name = "reportId"))
    @MapKeyJoinColumn(name = "student_uid")
    @Column(name = "attendance")
    @Fetch(FetchMode.SUBSELECT)
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private Map<Long, Boolean> studentAttendance = new TreeMap<>();

    @ElementCollection
    @CollectionTable(
            name = "student_marks_map",
            joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyJoinColumn(name = "student_uid")
    @Column(name = "mark")
    @Fetch(FetchMode.SUBSELECT)
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private Map<Long, Integer> studentMarks = new TreeMap<>();
}

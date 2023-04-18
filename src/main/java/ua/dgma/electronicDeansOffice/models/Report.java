package ua.dgma.electronicDeansOffice.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The field |REPORT NAME| cannot be empty!")
    @Column(nullable = false)
    private String reportName;

    @NotNull(message = "The field |EVENT DATA| cannot be empty!")
    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EventData eventData;

    @NotNull(message = "The field |CREATED| cannot be empty!")
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime created;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updated;


    @ElementCollection
    @CollectionTable(
            name = "student_attendance_map",
            joinColumns = {
                    @JoinColumn(name = "report_id", referencedColumnName = "id")
            })
    @MapKeyJoinColumn(name = "student_uid")
    private Map<Student, Boolean> studentAttendance = new HashMap<>();

    @ElementCollection
    @CollectionTable(
            name = "student_marks_map",
            joinColumns = {
                    @JoinColumn(name = "report_id", referencedColumnName = "id")
            })
    @MapKeyJoinColumn(name = "student_uid")
    private Map<Student, Integer> studentMarks = new HashMap<>();

}

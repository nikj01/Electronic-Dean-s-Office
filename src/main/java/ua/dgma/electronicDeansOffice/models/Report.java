package ua.dgma.electronicDeansOffice.models;

import lombok.*;
import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.ValueGenerationType;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Table(name = "Reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Event event;

    @ElementCollection
    @CollectionTable(
            name = "student_attendance_map",
            joinColumns = {
                    @JoinColumn(name = "report_id", referencedColumnName = "id")
            }
    )
    @MapKeyJoinColumn(name = "student_uid")
    private Map<Student, Attendance> studentAttendance = new TreeMap<>();

    @ElementCollection
    @CollectionTable(
            name = "student_marks_map",
            joinColumns = {
                    @JoinColumn(name = "report_id", referencedColumnName = "id")
            }
    )
    @MapKeyJoinColumn(name = "student_uid")
    private Map<Student, Integer> studentMark = new HashMap<>();

}

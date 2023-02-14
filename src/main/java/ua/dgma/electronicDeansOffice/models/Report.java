package ua.dgma.electronicDeansOffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.ValueGenerationType;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "Reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @OneToOne
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

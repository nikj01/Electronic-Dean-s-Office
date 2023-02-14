package ua.dgma.electronicDeansOffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @OneToOne
    private Event event;

/*

*   IDEA: Map<Student, Attendance> and Map<Student, Integer>

*   private Map<Student, Attendance> studentAttendanceMap;
*
*   private Map<Student, Integer> studentMarks;

*/


}

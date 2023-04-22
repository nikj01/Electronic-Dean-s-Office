package ua.dgma.electronicDeansOffice.services.impl.data.student;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class DataForStudentAttendance {
    @NonNull
    private Long studentId;
    private Integer semester;
    private LocalDateTime searchFrom;

    public DataForStudentAttendance(@NonNull Long studentId,
                                    Integer semester) {
        this.studentId = studentId;
        this.semester = semester;
    }

    public DataForStudentAttendance(@NonNull Long studentId,
                                    Integer semester,
                                    LocalDateTime searchFrom) {
        this.studentId = studentId;
        this.semester = semester;
        this.searchFrom = searchFrom;
    }
}

package ua.dgma.electronicDeansOffice.services.impl.data.student;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class DataForStudentStatistics {
    @NonNull
    private Long studentId;
    private Integer semester;
    private LocalDateTime searchFrom;
    private LocalDateTime searchTo;

    public DataForStudentStatistics(@NonNull Long studentId,
                                    Integer semester,
                                    LocalDateTime searchFrom,
                                    LocalDateTime searchTo) {
        this.studentId = studentId;
        this.semester = semester;
        this.searchFrom = searchFrom;
        this.searchTo = searchTo;
    }
}

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
    private LocalDateTime searchFrom;
    private LocalDateTime searchTo;

    public DataForStudentStatistics(@NonNull Long studentId,
                                    LocalDateTime searchFrom,
                                    LocalDateTime searchTo) {
        this.studentId = studentId;
        this.searchFrom = searchFrom;
        this.searchTo = searchTo;
    }
}

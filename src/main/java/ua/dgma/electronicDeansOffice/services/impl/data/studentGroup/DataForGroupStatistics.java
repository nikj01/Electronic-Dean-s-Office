package ua.dgma.electronicDeansOffice.services.impl.data.studentGroup;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class DataForGroupStatistics {
    @NonNull
    private Long groupId;
    private LocalDateTime searchFrom;
    private LocalDateTime searchTo;

    public DataForGroupStatistics(@NonNull Long groupId,
                                  LocalDateTime searchFrom,
                                  LocalDateTime searchTo) {
        this.groupId = groupId;
        this.searchFrom = searchFrom;
        this.searchTo = searchTo;
    }
}

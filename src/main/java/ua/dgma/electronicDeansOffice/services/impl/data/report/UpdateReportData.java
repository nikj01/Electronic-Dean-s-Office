package ua.dgma.electronicDeansOffice.services.impl.data.report;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.dgma.electronicDeansOffice.models.Report;

@Data
@RequiredArgsConstructor
public class UpdateReportData {
    @NonNull
    private Long reportId;
    @NonNull
    private Report report;
}

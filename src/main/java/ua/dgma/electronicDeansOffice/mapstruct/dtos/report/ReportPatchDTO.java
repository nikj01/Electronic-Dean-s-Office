package ua.dgma.electronicDeansOffice.mapstruct.dtos.report;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class ReportPatchDTO {
    private Map<Long, Boolean> studentAttendance = new TreeMap<>();
    private Map<Long, Integer> studentMarks = new TreeMap<>();
}

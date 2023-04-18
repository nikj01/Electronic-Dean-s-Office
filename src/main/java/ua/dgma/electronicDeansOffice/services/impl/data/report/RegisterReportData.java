package ua.dgma.electronicDeansOffice.services.impl.data.report;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.dgma.electronicDeansOffice.models.Event;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

import java.util.Map;
import java.util.TreeMap;

@Data
@RequiredArgsConstructor
public class RegisterReportData {
    @NonNull
    private Event event;
    @NonNull
    private StudentGroup studentGroup;
//    @NonNull
    private Map<Long, Boolean> studentAttendance = new TreeMap<>();
//    @NonNull
    private Map<Long, Integer> studentMarks = new TreeMap<>();
}

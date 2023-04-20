package ua.dgma.electronicDeansOffice.mapstruct.dtos.report;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.eventData.EventDataGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

@Data
public class ReportGetDTO {
    private Long id;
    private String reportName;
    private StudentGroupSlimGetDTO studentGroup;
    private EventDataGetDTO eventData;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime created;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updated;
    private Map<Long, Boolean> studentAttendance = new TreeMap<>();
    private Map<Long, Integer> studentMarks = new TreeMap<>();
}

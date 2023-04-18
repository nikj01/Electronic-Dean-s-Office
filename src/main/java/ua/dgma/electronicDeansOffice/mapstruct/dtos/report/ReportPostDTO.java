package ua.dgma.electronicDeansOffice.mapstruct.dtos.report;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.event.EventSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;

import java.util.Map;

@Data
public class ReportPostDTO {
    private EventSlimGetDTO event;
    private StudentGroupSlimGetDTO studentGroup;
    private Map<Long, Boolean> studentAttendance;
    private Map<Long, Integer> studentMarks;
}

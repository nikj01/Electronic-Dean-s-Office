package ua.dgma.electronicDeansOffice.mapstruct.dtos.report;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentSlimGetDTO;

import java.util.Map;
import java.util.TreeMap;

@Data
public class ReportPatchDTO {
    private Map<StudentSlimGetDTO, Boolean> studentAttendance = new TreeMap<>();
    private Map<StudentSlimGetDTO, Integer> studentMarks = new TreeMap<>();
}

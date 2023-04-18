package ua.dgma.electronicDeansOffice.mapstruct.dtos.report;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.eventData.EventDataGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentSlimGetDTO;

import javax.persistence.ElementCollection;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

@Data
public class ReportGetDTO {
    private Long id;
    private String reportName;
    private EventDataGetDTO eventData;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime created;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updated;
    @ElementCollection
    @JsonIdentityInfo(   generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "uid")
    @JsonIdentityReference(alwaysAsId = true)
    private Map<StudentSlimGetDTO, Boolean> studentAttendance = new TreeMap<>();
    @ElementCollection
    @JsonIdentityInfo(   generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "uid")
    @JsonIdentityReference(alwaysAsId = true)
    private Map<StudentSlimGetDTO, Integer> studentMarks = new TreeMap<>();;
}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.extractWithGrades;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.EventTypeEnum;

import java.time.LocalDateTime;

@Data
public class Extract {
    private String pageName;
    private EventTypeEnum eventType;
    private TeacherSlimGetDTO teacher;
    private LocalDateTime eventDate;
    private Integer grade;
}

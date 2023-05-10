package ua.dgma.electronicDeansOffice.mapstruct.dtos.eventData;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ua.dgma.electronicDeansOffice.models.EventTypeEnum;

import java.time.LocalDateTime;

@Data
public class EventDataGetDTO {
    private Long id;
    private Integer semester;
    private Long pageId;
    private String pageName;
    private String teachersData;
    private String eventTheme;
    private String eventDescription;
    private EventTypeEnum eventType;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime eventDate;
}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.event;

import lombok.Data;
import ua.dgma.electronicDeansOffice.models.EventTypeEnum;

import java.time.LocalDateTime;

@Data
public class EventSlimGetDTO {
    private Long id;
    private Integer semester;
    private String eventTheme;
    private EventTypeEnum eventType;
    private LocalDateTime date;
}

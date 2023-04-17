package ua.dgma.electronicDeansOffice.mapstruct.dtos.event;

import lombok.Data;
import ua.dgma.electronicDeansOffice.models.EventTypeEnum;

import java.time.LocalDate;

@Data
public class EventSlimGetDTO {
    private Long id;
    private String eventTheme;
    private EventTypeEnum eventType;
    private LocalDate date;
}

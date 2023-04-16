package ua.dgma.electronicDeansOffice.mapstruct.dtos.event;

import ua.dgma.electronicDeansOffice.models.EventType;

import java.time.LocalDate;

public class EventSlimGetDTO {
    private Long id;
    private String eventTheme;
    private EventType eventType;
    private LocalDate date;
}

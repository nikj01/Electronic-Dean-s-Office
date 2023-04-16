package ua.dgma.electronicDeansOffice.mapstruct.dtos.event;

import ua.dgma.electronicDeansOffice.models.EventType;
import ua.dgma.electronicDeansOffice.models.JournalPage;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

import java.time.LocalDate;
import java.util.List;

public class EventGetDTO {
    private Long id;
    private String eventTheme;
    private String description;
    private EventType eventType;
    private List<StudentGroup> studentGroups;
    private LocalDate date;
    private JournalPage page;
}

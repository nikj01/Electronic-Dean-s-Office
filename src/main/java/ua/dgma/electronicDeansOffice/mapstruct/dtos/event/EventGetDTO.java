package ua.dgma.electronicDeansOffice.mapstruct.dtos.event;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPageSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.EventTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventGetDTO {
    private Long id;
    private String eventTheme;
    private String description;
    private EventTypeEnum eventType;
    private List<StudentGroupSlimGetDTO> studentGroups;
    private LocalDateTime date;
    private JournalPageSlimGetDTO page;
}

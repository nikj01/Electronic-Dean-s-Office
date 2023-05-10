package ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.event.EventSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalSlimGetDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JournalPageGetDto {
    private Long id;
    private String pageName;
    private List<StudentGroupSlimGetDTO> studentGroups;
    private List<EventSlimGetDTO> events;
    private TeachersJournalSlimGetDTO journal;
    private boolean archive;
    private LocalDateTime wasArchived;
}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.Event;
import ua.dgma.electronicDeansOffice.models.Report;

import java.util.List;

@Data
public class JournalPageGetDto {
    private Long id;
    private String pageName;
    private List<StudentGroupSlimGetDTO> studentGroups;
    private List<Event> events;
    private List<Report> reports;
    private TeachersJournalSlimGetDTO journal;
    private boolean archive;
}

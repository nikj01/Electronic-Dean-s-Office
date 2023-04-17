package ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPageSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;

import java.util.List;

@Data
public class TeachersJournalGetDTO {
    private Long id;
    private PersonSlimGetDTO teacher;
    private String deleted;
    private List<JournalPageSlimGetDTO> pages;
}

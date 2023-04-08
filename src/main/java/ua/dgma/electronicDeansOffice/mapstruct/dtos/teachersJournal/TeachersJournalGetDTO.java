package ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal;

import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPageSlimGetDTO;

import java.util.List;

public class TeachersJournalGetDTO {
    private String comment;
    private List<JournalPageSlimGetDTO> pages;
}

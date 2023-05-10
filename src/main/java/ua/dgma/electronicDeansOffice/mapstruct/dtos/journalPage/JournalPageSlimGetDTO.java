package ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JournalPageSlimGetDTO {
    private Long id;
    private String pageName;
    private boolean archive;
    private LocalDateTime wasArchived;
}

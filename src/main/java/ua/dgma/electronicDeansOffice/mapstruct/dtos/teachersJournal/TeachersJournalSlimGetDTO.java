package ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;

import java.time.LocalDateTime;

@Data
public class TeachersJournalSlimGetDTO {
    private Long id;
    private PersonSlimGetDTO teacher;
    private boolean deleted;
    private LocalDateTime wasDeleted;
}

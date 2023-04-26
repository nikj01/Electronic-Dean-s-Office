package ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalSlimGetDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class JournalPagePostDto {
    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String pageName;
    @NotNull(message = "The field |TEACHER`S JOURNAL| cannot be empty!")
    private TeachersJournalSlimGetDTO journal;
    private Set<StudentGroupSlimGetDTO> studentGroups;

}

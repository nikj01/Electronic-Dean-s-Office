package ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class JournalPagePatchDto {
    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String pageName;
    private List<StudentGroupSlimGetDTO> studentGroups;
    private boolean archive;
}

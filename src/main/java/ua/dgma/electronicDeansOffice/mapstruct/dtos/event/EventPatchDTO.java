package ua.dgma.electronicDeansOffice.mapstruct.dtos.event;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.EventTypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventPatchDTO {
    @NotBlank(message = "The field |EVENT THEME| cannot be empty!")
    private String eventTheme;
    @NotBlank(message = "The field |DESCRIPTION| cannot be empty!")
    private String description;
    @NotNull(message = "The field |EVENT TYPE| cannot be empty!")
    private EventTypeEnum eventType;
    private List<StudentGroupSlimGetDTO> studentGroups;
    @NotNull(message = "The field |DATE| cannot be empty!")
    private LocalDateTime date;
}

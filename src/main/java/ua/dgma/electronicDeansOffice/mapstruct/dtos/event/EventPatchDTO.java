package ua.dgma.electronicDeansOffice.mapstruct.dtos.event;

import ua.dgma.electronicDeansOffice.models.EventType;
import ua.dgma.electronicDeansOffice.models.JournalPage;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class EventPatchDTO {
    @NotBlank(message = "The field |EVENT THEME| cannot be empty!")
    private String eventTheme;
    @NotBlank(message = "The field |DESCRIPTION| cannot be empty!")
    private String description;
    @NotNull(message = "The field |EVENT TYPE| cannot be empty!")
    private EventType eventType;
    private List<StudentGroup> studentGroups;
    @NotNull(message = "The field |DATE| cannot be empty!")
    private LocalDate date;
    @NotNull(message = "The field |JOURNAL PAGE| cannot be empty!")
    private JournalPage page;
}

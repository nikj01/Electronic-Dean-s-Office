package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FacultySlimGetDTO {
    private Long id;
    private String name;
    private boolean deleted;
    private LocalDateTime wasDeleted;
}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentGroupSlimGetDTO {
    private Long id;
    private String name;
    private boolean deleted;
    private LocalDateTime wasDeleted;
}

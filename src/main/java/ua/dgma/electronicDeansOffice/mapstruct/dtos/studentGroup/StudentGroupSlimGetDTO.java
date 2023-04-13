package ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup;

import lombok.Data;

@Data
public class StudentGroupSlimGetDTO {
    private Long id;
    private String name;
    private boolean deleted;
}

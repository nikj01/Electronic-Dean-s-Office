package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import lombok.Data;

@Data
public class FacultySlimGetDTO {
    private Long id;
    private String name;
    private boolean deleted;

}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.Data;

@Data
public class DepartmentSlimGetDTO {
    private Long id;
    private String name;
    private boolean deleted;
}

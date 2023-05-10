package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepartmentSlimGetDTO {
    private Long id;
    private String name;
    private boolean deleted;
    private LocalDateTime wasDeleted;
}

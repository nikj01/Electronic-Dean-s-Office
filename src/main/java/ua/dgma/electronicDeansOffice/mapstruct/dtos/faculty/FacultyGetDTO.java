package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class FacultyGetDTO {
    private Long id;
    private String name;
    private Set<DepartmentSlimGetDTO> departments;
    private Set<PersonSlimGetDTO> deaneryWorkers;
    private boolean deleted;
    private LocalDateTime wasDeleted;
}

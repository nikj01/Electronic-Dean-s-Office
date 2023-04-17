package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DepartmentPatchDTO {
    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String name;
    @NotNull(message = "The field |FACULTY| cannot be empty!")
    private FacultySlimGetDTO faculty;
    private boolean deleted;
}

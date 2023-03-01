package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentsSlimGetDTO;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FacultyGetDTO extends FacultySlimGetDTO{

    @NotNull
    private DepartmentsSlimGetDTO departments;
}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
public class DepartmentGetDTO extends DepartmentSlimGetDTO {

    @NotEmpty
    private Set<StudentGroupSlimGetDTO> studentGroups;
}

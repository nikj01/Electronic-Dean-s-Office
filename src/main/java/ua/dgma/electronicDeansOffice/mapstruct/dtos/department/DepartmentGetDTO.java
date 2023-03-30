package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupsSlimGetDTO;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
public class DepartmentGetDTO extends DepartmentSlimGetDTO {

    private FacultySlimGetDTO faculty;
//    private List<StudentGroupSlimGetDTO> studentGroups;

}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupsSlimGetDTO;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class DepartmentGetDTO extends DepartmentSlimGetDTO {

    @NonNull
//    private Set<StudentGroupSlimGetDTO> studentGroups;
    private StudentGroupsSlimGetDTO studentGroups;
}

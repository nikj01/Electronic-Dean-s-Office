package ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
public class StudentGroupsSlimGetDTO {

    private Set<StudentGroupSlimGetDTO> studentGroups;
}

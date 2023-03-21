package ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class StudentGroupsSlimGetDTO {

    @NonNull
    private Set<StudentGroupSlimGetDTO> studentGroups;
}

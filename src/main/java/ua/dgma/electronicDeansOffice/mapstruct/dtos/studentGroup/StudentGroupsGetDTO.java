package ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentsSlimGetDTO;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class StudentGroupsGetDTO extends StudentGroupSlimGetDTO {

    @NonNull
    private List<StudentGroupGetDTO> studentGroups;
}

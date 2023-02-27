package ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class StudentGroupDTO extends StudentGroupSlimGetDTO {

    @NotEmpty
    private PersonSlimGetDTO groupLeader;

//    private Set<StudentDTO> students = new TreeSet<>();

//    @NotEmpty(message = "The field |CURATOR| cannot be empty!")
//    private Teacher curator;
//
//
//    @NotEmpty(message = "The field |DEPARTMENT| cannot be empty!")
//    private Department department;
}

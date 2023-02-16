package ua.dgma.electronicDeansOffice.mapstruct.dtos.StudentGroup;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonGetDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class StudentGroupDTO {
    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String name;

    @NotEmpty(message = "The field |GROUP LEADER| cannot be empty!")
    private PersonGetDTO groupLeader;

//    private Set<StudentDTO> students = new TreeSet<>();

//    @NotEmpty(message = "The field |CURATOR| cannot be empty!")
//    private Teacher curator;
//
//
//    @NotEmpty(message = "The field |DEPARTMENT| cannot be empty!")
//    private Department department;
}

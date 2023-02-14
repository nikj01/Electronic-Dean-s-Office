package ua.dgma.electronicDeansOffice.dtos;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.models.Student;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public class StudentGroupDTO {
    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String name;

    @NotEmpty(message = "The field |GROUP LEADER| cannot be empty!")
    private PersonDTO groupLeader;

//    private Set<StudentDTO> students = new TreeSet<>();

//    @NotEmpty(message = "The field |CURATOR| cannot be empty!")
//    private Teacher curator;
//
//
//    @NotEmpty(message = "The field |DEPARTMENT| cannot be empty!")
//    private Department department;
}

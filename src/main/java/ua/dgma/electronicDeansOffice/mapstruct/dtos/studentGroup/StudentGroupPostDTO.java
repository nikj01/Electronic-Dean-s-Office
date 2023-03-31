package ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherSlimGetDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class StudentGroupPostDTO {

//    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String name;
//    @NotNull(message = "The field |DEPARTMENT| cannot be empty!")
    private DepartmentSlimGetDTO department;
//    @NotNull(message = "The field |CURATOR| cannot be empty!")
    private PersonSlimGetDTO curator;
    private PersonSlimGetDTO groupLeader;
    private List<StudentPostDTO> students;
}

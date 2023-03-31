package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherPostDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class DepartmentPatchDTO {

//    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String name;
//    @NotEmpty(message = "The field |FACULTY| cannot be empty!")
    private FacultySlimGetDTO faculty;
    private boolean deleted;
}

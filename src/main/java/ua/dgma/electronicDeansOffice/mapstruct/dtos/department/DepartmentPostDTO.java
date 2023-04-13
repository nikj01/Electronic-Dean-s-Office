package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherPostDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DepartmentPostDTO {
    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String name;
    @NotNull(message = "The field |FACULTY| cannot be empty!")
    private FacultySlimGetDTO faculty;
    private List<TeacherPostDTO> teachers;
}

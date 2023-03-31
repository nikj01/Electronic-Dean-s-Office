package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherPostDTO;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
public class DepartmentPostDTO {

//    @NotBlank(message = "The field |NAME| cannot be empty!")
    private String name;
//    @NotEmpty(message = "The field |FACULTY| cannot be empty!")
    private FacultySlimGetDTO faculty;
    private List<StudentGroupPostDTO> studentGroups;
    private List<TeacherPostDTO> teachers;
}

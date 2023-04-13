package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherSlimGetDTO;

import java.util.List;

@Data
public class DepartmentGetDTO extends DepartmentSlimGetDTO {
    private Long id;
    private String name;
    private FacultySlimGetDTO faculty;
    private List<StudentGroupSlimGetDTO> studentGroups;
    private List<TeacherSlimGetDTO> teachers;
    private boolean deleted;
}

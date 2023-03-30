package ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.Student;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Data
public class StudentGroupGetDTO extends StudentGroupSlimGetDTO{

    private StudentSlimGetDTO groupLeader;
    private PersonSlimGetDTO curator;
    private DepartmentSlimGetDTO department;
    private List<StudentSlimGetDTO> students;

}
package ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.Student;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public class StudentGroupGetDTO extends StudentGroupSlimGetDTO{

//    @NotNull
//    private StudentSlimGetDTO groupLeader;
//    @NotNull
//    private Set<Student> students = new TreeSet<>();
//    @NotNull
//    private PersonSlimGetDTO curator;
//    @NotNull
//    private DepartmentSlimGetDTO department;

}
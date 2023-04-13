package ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;

import java.util.List;

@Data
public class StudentGroupGetDTO extends StudentGroupSlimGetDTO{
    private Long id;
    private PersonSlimGetDTO groupLeader;
    private PersonSlimGetDTO curator;
    private DepartmentSlimGetDTO department;
    private List<PersonSlimGetDTO> students;

}
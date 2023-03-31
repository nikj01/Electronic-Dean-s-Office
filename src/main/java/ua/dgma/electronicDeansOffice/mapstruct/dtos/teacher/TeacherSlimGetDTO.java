package ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;

@Data
public class TeacherSlimGetDTO extends PersonSlimGetDTO {

    private DepartmentSlimGetDTO department;
}

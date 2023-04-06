package ua.dgma.electronicDeansOffice.mapstruct.dtos.student;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.PersonRoleEnum;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class StudentSlimGetDTO {

    private Long uid;
    private String surname;
    private String name;
    private String patronymic;
    private StudentGroupSlimGetDTO studentGroup;
    private boolean deleted;
}

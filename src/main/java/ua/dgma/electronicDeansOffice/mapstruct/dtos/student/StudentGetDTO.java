package ua.dgma.electronicDeansOffice.mapstruct.dtos.student;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.PersonRoleEnum;

import java.sql.Date;
import java.util.List;

@Data
public class StudentGetDTO {
    private Long uid;
    private String surname;
    private String name;
    private String patronymic;
    private List<PersonRoleEnum> personRoles;
    private StudentGroupSlimGetDTO studentGroup;
    private Date dateOfBirth;
    private String email;
    private boolean deleted;
}

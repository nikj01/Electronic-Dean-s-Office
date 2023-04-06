package ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.PersonRoleEnum;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Data
public class DeaneryWorkerGetDTO {

    private Long uid;
    private String surname;
    private String name;
    private String patronymic;
    private List<PersonRoleEnum> personRoles;
    private FacultySlimGetDTO faculty;
    private Date dateOfBirth;
    private String email;
    private String password;
    private boolean deleted;
}

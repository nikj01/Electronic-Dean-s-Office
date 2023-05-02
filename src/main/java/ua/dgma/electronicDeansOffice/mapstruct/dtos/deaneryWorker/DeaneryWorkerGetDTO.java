package ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.models.PersonRoleEnum;

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
    private boolean deleted;
}

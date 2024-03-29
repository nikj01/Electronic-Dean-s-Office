package ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.PersonRoleEnum;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeacherGetDTO {
    private Long uid;
    private String surname;
    private String name;
    private String patronymic;
    private List<PersonRoleEnum> personRoles;
    private DepartmentSlimGetDTO department;
    private List<StudentGroupSlimGetDTO> studentGroups;
    private Date dateOfBirth;
    private String email;
    private boolean deleted;
    private LocalDateTime wasDeleted;}

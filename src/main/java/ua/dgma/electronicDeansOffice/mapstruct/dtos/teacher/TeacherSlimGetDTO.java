package ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;

import java.time.LocalDateTime;

@Data
public class TeacherSlimGetDTO {
    private Long uid;
    private String surname;
    private String name;
    private String patronymic;
    private DepartmentSlimGetDTO department;
    private boolean deleted;
    private LocalDateTime wasDeleted;}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;

import javax.validation.constraints.NotNull;

@Data
public class DeaneryWorkerSlimGetDTO {

    private Long uid;
    private String surname;
    private String name;
    private String patronymic;
    private FacultySlimGetDTO faculty;
    private boolean deleted;
}

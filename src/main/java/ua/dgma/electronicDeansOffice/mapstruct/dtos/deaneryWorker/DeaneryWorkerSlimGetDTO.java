package ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;

@Data
public class DeaneryWorkerSlimGetDTO {
    private Long uid;
    private String surname;
    private String name;
    private String patronymic;
    private FacultySlimGetDTO faculty;
    private boolean deleted;
}

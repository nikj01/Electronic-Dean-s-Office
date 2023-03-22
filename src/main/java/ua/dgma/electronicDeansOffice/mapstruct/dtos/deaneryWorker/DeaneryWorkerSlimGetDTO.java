package ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;

import javax.validation.constraints.NotNull;

@Data
public class DeaneryWorkerSlimGetDTO extends PersonSlimGetDTO {

    private FacultySlimGetDTO faculty;
}

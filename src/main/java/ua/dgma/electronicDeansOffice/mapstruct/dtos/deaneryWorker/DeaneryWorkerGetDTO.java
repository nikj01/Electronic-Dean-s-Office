package ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonGetDTO;

import javax.validation.constraints.NotNull;

@Data
public class DeaneryWorkerGetDTO extends PersonGetDTO {

    private FacultySlimGetDTO faculty;
}

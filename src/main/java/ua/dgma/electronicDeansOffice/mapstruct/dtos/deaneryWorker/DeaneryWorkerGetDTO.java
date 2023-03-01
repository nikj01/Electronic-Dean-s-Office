package ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonGetDTO;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DeaneryWorkerGetDTO extends PersonGetDTO {

    @NotNull
    private FacultySlimGetDTO faculty;
}

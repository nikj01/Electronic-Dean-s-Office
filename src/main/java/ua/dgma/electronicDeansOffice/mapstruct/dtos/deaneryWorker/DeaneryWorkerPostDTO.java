package ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker;

import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultyPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonPostDTO;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DeaneryWorkerPostDTO extends PersonPostDTO {

    @NotNull(message = "The field |FACULTY| cannot be empty!")
    private FacultyPostDTO faculty;
}

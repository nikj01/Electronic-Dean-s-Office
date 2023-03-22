package ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultyPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonPostDTO;

import javax.validation.constraints.NotNull;

@Data
public class DeaneryWorkerPostDTO extends PersonPostDTO {

    private FacultyPostDTO faculty;
}

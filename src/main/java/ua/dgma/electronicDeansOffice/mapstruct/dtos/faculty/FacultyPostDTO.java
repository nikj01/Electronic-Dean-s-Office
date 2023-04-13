package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.DeaneryWorkerPostDTO;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class FacultyPostDTO {
    @NotBlank(message = "The field |FACULTY NAME| cannot be empty!")
    private String name;
    private List<DeaneryWorkerPostDTO> deaneryWorkers;
}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.DeaneryWorkerPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentPostDTO;
import ua.dgma.electronicDeansOffice.models.Department;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Data
public class FacultyPostDTO {

    @NotBlank(message = "The field |FACULTY NAME| cannot be empty!")
    private String name;
    private List<DeaneryWorkerPostDTO> deaneryWorkers;
}

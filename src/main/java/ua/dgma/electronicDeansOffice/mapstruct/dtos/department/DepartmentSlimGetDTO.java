package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultyGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;

import javax.validation.constraints.NotBlank;


@Data
public class DepartmentSlimGetDTO {

    private String name;
    private boolean deleted;
}

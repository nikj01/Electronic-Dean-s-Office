package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentsSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.Department;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
public class FacultyGetDTO {

    private String name;
    private Set<DepartmentSlimGetDTO> departments;

}

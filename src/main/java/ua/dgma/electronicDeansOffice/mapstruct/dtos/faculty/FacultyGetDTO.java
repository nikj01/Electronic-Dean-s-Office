package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentsSlimGetDTO;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class FacultyGetDTO extends FacultySlimGetDTO{

    @NonNull
    @JsonManagedReference("departments")
    private Set<DepartmentSlimGetDTO> departments = new HashSet<>();
}

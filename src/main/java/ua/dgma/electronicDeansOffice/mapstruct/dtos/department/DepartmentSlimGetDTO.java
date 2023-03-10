package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultyGetDTO;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class DepartmentSlimGetDTO implements Comparable<DepartmentSlimGetDTO>{

    @NotBlank
    private String name;

    @Override
    public int compareTo(DepartmentSlimGetDTO o) {
        if (this.name.compareToIgnoreCase(o.getName()) > 0)
            return 1;
        else if (this.name.compareToIgnoreCase(o.getName()) < 0) {
            return -1;
        } else
            return 0;
    }
}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.dgma.electronicDeansOffice.models.Department;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class FacultySlimGetDTO implements Comparable<FacultySlimGetDTO>{

    @NotBlank
    private String name;

    @Override
    public int compareTo(FacultySlimGetDTO o) {
        if(this.name.compareToIgnoreCase(o.getName()) > 0) {
            return 1;
        } else if (this.name.compareToIgnoreCase(o.getName()) < 0) {
            return -1;
        } else
            return 0;
    }
}

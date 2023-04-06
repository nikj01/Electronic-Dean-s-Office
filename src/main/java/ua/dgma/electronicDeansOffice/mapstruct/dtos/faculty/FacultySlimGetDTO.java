package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.dgma.electronicDeansOffice.models.Department;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class FacultySlimGetDTO {

    private String name;
    private boolean deleted;

}

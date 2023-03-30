package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.*;

import java.util.List;
import java.util.Set;


@Data
public class DepartmentsSlimGetDTO {

    private List<DepartmentSlimGetDTO> departments;
}

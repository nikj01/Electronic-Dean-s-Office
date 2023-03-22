package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.*;

import java.util.List;

@Data
public class DepartmentsGetDTO {

    private List<DepartmentGetDTO> departments;
}

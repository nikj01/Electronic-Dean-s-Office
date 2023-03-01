package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class DepartmentsSlimGetDTO {

    @NotNull
    private List<DepartmentSlimGetDTO> departments;
}

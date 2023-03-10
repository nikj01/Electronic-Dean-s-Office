package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class DepartmentsSlimGetDTO {

    @NonNull
    private List<DepartmentSlimGetDTO> departments;
}

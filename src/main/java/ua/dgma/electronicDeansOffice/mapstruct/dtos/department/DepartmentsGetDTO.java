package ua.dgma.electronicDeansOffice.mapstruct.dtos.department;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class DepartmentsGetDTO {

    @NonNull
    private List<DepartmentGetDTO> departments;
}

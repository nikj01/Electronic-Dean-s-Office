package ua.dgma.electronicDeansOffice.services.impl.data.department;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.dgma.electronicDeansOffice.models.Department;

@Data
@RequiredArgsConstructor
public class UpdateDepartmentData {
    @NonNull
    private Long id;
    @NonNull
    private Department updatedDepartment;
}

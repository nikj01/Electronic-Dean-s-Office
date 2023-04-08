package ua.dgma.electronicDeansOffice.services.impl.data.department;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.dgma.electronicDeansOffice.models.Department;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UpdateDepartmentData {
    @NonNull
    private String name;
    @NonNull
    private Department updatedDepartment;
}
package ua.dgma.electronicDeansOffice.services.impl.data.department;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Department;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RegisterDepartmentData {
    @NonNull
    private Department newDepartment;
    @NonNull
    private BindingResult bindingResult;
}

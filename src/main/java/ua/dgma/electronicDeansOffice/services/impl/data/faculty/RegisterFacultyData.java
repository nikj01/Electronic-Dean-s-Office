package ua.dgma.electronicDeansOffice.services.impl.data.faculty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Faculty;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RegisterFacultyData {
    @NonNull
    private Faculty newFaculty;
    @NonNull
    private BindingResult bindingResult;
}

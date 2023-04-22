package ua.dgma.electronicDeansOffice.services.impl.data.faculty;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Faculty;

@Data
@RequiredArgsConstructor
public class UpdateFacultyData {
    @NonNull
    private Long id;
    @NonNull
    private Faculty updatedFaculty;
    @NonNull
    private BindingResult bindingResult;
}

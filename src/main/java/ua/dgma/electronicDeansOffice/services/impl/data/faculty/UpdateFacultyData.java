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
public class UpdateFacultyData {
    @NonNull
    private String name;
    @NonNull
    private Faculty updatedFaculty;
    @NonNull
    private BindingResult bindingResult;
}

package ua.dgma.electronicDeansOffice.services.impl.data.studentGroup;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

@Data
@RequiredArgsConstructor
public class RegisterStudentGroupData {
    @NonNull
    private StudentGroup newStudentGroup;
    @NonNull
    private BindingResult bindingResult;
}

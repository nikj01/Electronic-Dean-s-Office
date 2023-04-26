package ua.dgma.electronicDeansOffice.services.impl.data.studentGroup;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

@Data
@RequiredArgsConstructor
public class UpdateStudentGroupData {
    @NonNull
    private Long id;
    @NonNull
    private StudentGroup updatedStudentGroup;
}

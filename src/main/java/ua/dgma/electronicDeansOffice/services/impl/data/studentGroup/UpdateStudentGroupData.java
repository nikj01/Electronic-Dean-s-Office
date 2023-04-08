package ua.dgma.electronicDeansOffice.services.impl.data.studentGroup;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UpdateStudentGroupData {
    @NonNull
    private String name;
    @NonNull
    private StudentGroup updatedStudentGroup;
}

package ua.dgma.electronicDeansOffice.utill.validators.data;

import lombok.*;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class FacultyValidationData {
    @NonNull
    private Faculty faculty;
    @NonNull
    private FacultyRepository facultyRepository;
    @NonNull
    private PeopleRepository deaneryWorkerRepository;
}

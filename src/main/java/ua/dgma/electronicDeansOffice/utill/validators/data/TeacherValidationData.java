package ua.dgma.electronicDeansOffice.utill.validators.data;

import lombok.*;
import org.springframework.validation.Errors;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;

@Getter
@Setter
@RequiredArgsConstructor
public class TeacherValidationData {
    @NonNull
    private Teacher teacher;
    @NonNull
    private PeopleRepository peopleRepository;
    @NonNull
    private DepartmentRepository departmentRepository;
    @NonNull
    private Errors errors;
}

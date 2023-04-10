package ua.dgma.electronicDeansOffice.utill.validators.data;

import lombok.*;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;

@Getter
@Setter
@RequiredArgsConstructor
public class DepartmentValidationData {

    @NonNull
    private Department department;
    @NonNull
    private DepartmentRepository departmentRepository;
    @NonNull
    private FacultyRepository facultyRepository;
    @NonNull
    private PeopleRepository peopleRepository;
}

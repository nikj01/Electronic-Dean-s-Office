package ua.dgma.electronicDeansOffice.utill.validators.data;

import lombok.*;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.*;

@Getter
@Setter
@RequiredArgsConstructor
public class StudentGroupValidationData {
    @NonNull
    private StudentGroup studentGroup;
    @NonNull
    private StudentGroupRepository studentGroupRepository;
    @NonNull
    private StudentRepository studentRepository;
    @NonNull
    private DepartmentRepository departmentRepository;
    @NonNull
    private TeacherRepository teacherRepository;
    @NonNull
    private PeopleRepository peopleRepository;

}

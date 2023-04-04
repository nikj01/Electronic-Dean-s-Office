package ua.dgma.electronicDeansOffice.utill.validators.data;

import lombok.*;
import org.springframework.validation.Errors;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.repositories.functional.GetDepartmentByNameInterface;
import ua.dgma.electronicDeansOffice.repositories.functional.GetStudentByUidInterface;
import ua.dgma.electronicDeansOffice.repositories.functional.GetStudentGroupByNameInterface;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class StudentGroupValidationData {

    @NonNull
    private StudentGroup studentGroup;
    @NonNull
    private StudentGroupRepository studentGroupRepository;
    @NonNull
    private DepartmentRepository departmentRepository;
    @NonNull
    private TeacherRepository teacherRepository;
    @NonNull
    private StudentRepository studentRepository;
}

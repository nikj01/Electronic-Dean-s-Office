package ua.dgma.electronicDeansOffice.utill.validators.data;

import lombok.*;
import org.springframework.validation.Errors;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;

@Getter
@Setter
@RequiredArgsConstructor
public class StudentValidationData {
    @NonNull
    private Student student;
    @NonNull
    private PeopleRepository peopleRepository;
    @NonNull
    private StudentRepository studentRepository;
    @NonNull
    private StudentGroupRepository studentGroupRepository;
    @NonNull
    private Errors errors;
}

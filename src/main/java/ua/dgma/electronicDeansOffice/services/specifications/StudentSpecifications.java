package ua.dgma.electronicDeansOffice.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.models.Student;

@Component
public interface StudentSpecifications extends PeopleSpecifications {
    Specification<Student> findStudentsByFacultyCriteria(Long facultyId);
}

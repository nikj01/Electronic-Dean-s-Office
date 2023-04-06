package ua.dgma.electronicDeansOffice.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.models.Teacher;

@Component
public interface TeacherSpecifications extends PeopleSpecifications, DeletedSpecification {
    Specification<Teacher> findTeachersByFacultyCriteria(String facultyName);
}

package ua.dgma.electronicDeansOffice.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

public interface StudentGroupSpecifications extends DeletedSpecification {
    Specification<StudentGroup> getStudentGroupByFacultyCriteria(String departmentName);
}

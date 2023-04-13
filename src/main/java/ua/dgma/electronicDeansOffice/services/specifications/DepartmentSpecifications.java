package ua.dgma.electronicDeansOffice.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import ua.dgma.electronicDeansOffice.models.Department;

public interface DepartmentSpecifications extends DeletedSpecification {
    Specification<Department> getDepartmentByFacultyCriteria(Long facultyId);
}

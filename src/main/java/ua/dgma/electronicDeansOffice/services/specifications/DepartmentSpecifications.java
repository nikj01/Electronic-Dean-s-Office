package ua.dgma.electronicDeansOffice.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;

public interface DepartmentSpecifications extends DeletedSpecification {
    Specification<Department> getSpecForDepartments(FindAllData data);
}

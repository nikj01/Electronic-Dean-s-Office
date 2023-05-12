package ua.dgma.electronicDeansOffice.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;

public interface StudentGroupSpecifications extends DeletedSpecification {
    Specification<StudentGroup> getSpecForStudentGroups(FindAllData data);
}

    package ua.dgma.electronicDeansOffice.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public interface DeletedSpecification<P> {
    Specification<P> getObjectByDeletedCriteria(Boolean isDeleted);
}

package ua.dgma.electronicDeansOffice.services.specifications;

import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@Getter
@Setter
@NoArgsConstructor
@Component
public class Specifications<P> {

    @NonNull
    private Root<P> root;
    @NonNull
    private CriteriaQuery<P> query;
    @NonNull
    private CriteriaBuilder builder;

    public Specification<P> getObjectByDeletedCriteria(Boolean isDeleted) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("deleted"), isDeleted);
        };
    }

    public Specification<P> getStudentGroupByCurator(Long curatorUid) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("curator"), curatorUid);
        };
    }

    public Specification<P> getStudentGroupByDepartment(String departmentName) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("department").get("name"), departmentName);
        };
    }
}

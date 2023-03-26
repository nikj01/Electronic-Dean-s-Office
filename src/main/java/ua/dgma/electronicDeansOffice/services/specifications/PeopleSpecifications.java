package ua.dgma.electronicDeansOffice.services.specifications;

import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.models.Person;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.lang.reflect.ParameterizedType;


@Getter
@Setter
@NoArgsConstructor
@Component
public class PeopleSpecifications<P extends Person> {

    @NonNull
    private Root<P> root;
    @NonNull
    private CriteriaQuery<P> query;
    @NonNull
    private CriteriaBuilder builder;

    public Class<P> getPersistentClass() {
        return (Class<P>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Specification<P> getPeopleByDeletedCriteria(Boolean isDeleted) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("deleted"), isDeleted);
        };
    }
}

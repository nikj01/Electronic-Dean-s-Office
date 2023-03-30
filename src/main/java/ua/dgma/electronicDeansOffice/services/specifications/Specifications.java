package ua.dgma.electronicDeansOffice.services.specifications;

import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.Teacher;

import javax.lang.model.element.Name;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;


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
    @NonNull
    private EntityManager entityManager;

    public Specification<P> getObjectByDeletedCriteria(Boolean isDeleted) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("deleted"), isDeleted);
        };
    }

    public Specification<P> findDeaneryWorkersByFacultyCriteria(String facultyName) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("faculty").get("name"), facultyName);
        };

    }

    public Specification<P> findTeachersByFacultyCriteria(String facultyName) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("department").get("faculty").get("name"), facultyName);
        };
    }


    public Specification<P> findStudentsByFacultyCriteria(String facultyName) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("studentGroup").get("department").get("faculty").get("name"), facultyName);
        };
    }

    public Specification<P> getStudentGroupByCuratorCriteria(Long curatorUid) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("curator"), curatorUid);
        };
    }

    public Specification<P> getStudentGroupByDepartmentCriteria(String departmentName) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("department").get("name"), departmentName);
        };
    }
}

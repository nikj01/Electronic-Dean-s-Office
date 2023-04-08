package ua.dgma.electronicDeansOffice.services.specifications.impl;

import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.models.*;
import ua.dgma.electronicDeansOffice.services.specifications.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;


@Getter
@Setter
@NoArgsConstructor
@Component
public class SpecificationsImpl<P> implements DeaneryWorkerSpecifications,
                                              TeacherSpecifications,
                                              StudentSpecifications,
                                              StudentGroupSpecifications,
                                              DepartmentSpecifications {
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

    public Specification<DeaneryWorker> findDeaneryWorkersByFacultyCriteria(String facultyName) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("faculty").get("name"), facultyName);
        };

    }

    public Specification<Teacher> findTeachersByFacultyCriteria(String facultyName) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("department").get("faculty").get("name"), facultyName);
        };
    }


    public Specification<Student> findStudentsByFacultyCriteria(String facultyName) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("studentGroup").get("department").get("faculty").get("name"), facultyName);
        };
    }

    public Specification<StudentGroup> getStudentGroupByFacultyCriteria(String facultyName) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("department").get("faculty").get("name"), facultyName);
        };
    }
    public Specification<Department> getDepartmentByFacultyCriteria(String facultyName) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("faculty").get("name"), facultyName);
        };
    }
}

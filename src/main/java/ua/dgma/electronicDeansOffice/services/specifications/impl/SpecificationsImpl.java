package ua.dgma.electronicDeansOffice.services.specifications.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.models.*;
import ua.dgma.electronicDeansOffice.services.specifications.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@Getter
@Setter
@NoArgsConstructor
@Component
public class SpecificationsImpl<P> implements DeaneryWorkerSpecifications,
        TeacherSpecifications,
        StudentSpecifications,
        StudentGroupSpecifications,
        DepartmentSpecifications,
        TeachersJournalSpecifications {
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

    public Specification<DeaneryWorker> findDeaneryWorkersByFacultyCriteria(Long facultyId) {
        return (root, query, criteriaBuilder) -> {
            if (facultyId == null)
                return criteriaBuilder.isNotNull(root.get("faculty"));
            else
                return criteriaBuilder.equal(root.get("faculty").get("id"), facultyId);
        };

    }

    public Specification<Teacher> findTeachersByFacultyCriteria(Long facultyId) {
        return (root, query, criteriaBuilder) -> {
            if (facultyId == null)
                return criteriaBuilder.isNotNull(root.get("department").get("faculty"));
            else
                return criteriaBuilder.equal(root.get("department").get("faculty").get("id"), facultyId);
        };
    }

    public Specification<Student> findStudentsByFacultyCriteria(Long facultyId) {
        return (root, query, criteriaBuilder) -> {
            if (facultyId == null)
                return criteriaBuilder.isNotNull(root.get("studentGroup").get("department").get("faculty"));
            else
                return criteriaBuilder.equal(root.get("studentGroup").get("department").get("faculty").get("id"), facultyId);
        };
    }

    public Specification<StudentGroup> getStudentGroupByFacultyCriteria(Long facultyId) {
        return (root, query, criteriaBuilder) -> {
            if (facultyId == null)
                return criteriaBuilder.isNotNull(root.get("department").get("faculty"));
            else
                return criteriaBuilder.equal(root.get("department").get("faculty").get("id"), facultyId);
        };
    }

    public Specification<Department> getDepartmentByFacultyCriteria(Long facultyId) {
        return (root, query, criteriaBuilder) -> {
            if (facultyId == null)
                return criteriaBuilder.isNotNull(root.get("faculty"));
            else
                return criteriaBuilder.equal(root.get("faculty").get("id"), facultyId);
        };
    }

    public Specification<TeachersJournal> getTeacherJournalByFacultyCriteria(Long facultyId) {
        return (root, query, criteriaBuilder) -> {
            if (facultyId == null)
                return criteriaBuilder.isNotNull(root.get("teacher").get("department").get("faculty"));
            else
                return criteriaBuilder.equal(root.get("teacher").get("department").get("faculty").get("id"), facultyId);
        };
    }
}

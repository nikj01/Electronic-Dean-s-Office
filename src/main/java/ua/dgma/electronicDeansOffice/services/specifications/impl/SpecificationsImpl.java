package ua.dgma.electronicDeansOffice.services.specifications.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.models.*;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
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

    public Specification<DeaneryWorker> getSpecForDeaneryWorkers(FindAllData data) {
        return Specification
                .where(findDeaneryWorkersByFacultyCriteria(data.getFacultyId())
                        .and((Specification<DeaneryWorker>) getObjectByDeletedCriteria(data.getDeleted())));
    }

    private Specification<DeaneryWorker> findDeaneryWorkersByFacultyCriteria(Long facultyId) {
        return (root, query, criteriaBuilder) -> {
            if (facultyId == null)
                return criteriaBuilder.isNotNull(root.get("faculty"));
            else
                return criteriaBuilder.equal(root.get("faculty").get("id"), facultyId);
        };

    }

    public Specification<Teacher> getSpecForTeachers(FindAllData data) {
        return Specification
                .where(findTeachersByFacultyCriteria(data.getFacultyId())
                        .and((Specification<Teacher>) getObjectByDeletedCriteria(data.getDeleted())));
    }

    private Specification<Teacher> findTeachersByFacultyCriteria(Long facultyId) {
        return (root, query, criteriaBuilder) -> {
            if (facultyId == null)
                return criteriaBuilder.isNotNull(root.get("department").get("faculty"));
            else
                return criteriaBuilder.equal(root.get("department").get("faculty").get("id"), facultyId);
        };
    }

    public Specification<Student> getSpecForStudents(FindAllData data) {
        return Specification
                .where(findStudentsByFacultyCriteria(data.getFacultyId())
                        .and((Specification<Student>) getObjectByDeletedCriteria(data.getDeleted())));
    }

    private Specification<Student> findStudentsByFacultyCriteria(Long facultyId) {
        return (root, query, criteriaBuilder) -> {
            if (facultyId == null)
                return criteriaBuilder.isNotNull(root.get("studentGroup").get("department").get("faculty"));
            else
                return criteriaBuilder.equal(root.get("studentGroup").get("department").get("faculty").get("id"), facultyId);
        };
    }

    public Specification<StudentGroup> getSpecForStudentGroups(FindAllData data) {
        return Specification
                .where(findStudentGroupsByFacultyCriteria(data.getFacultyId())
                        .and((Specification<StudentGroup>) getObjectByDeletedCriteria(data.getDeleted())));
    }

    private Specification<StudentGroup> findStudentGroupsByFacultyCriteria(Long facultyId) {
        return (root, query, criteriaBuilder) -> {
            if (facultyId == null)
                return criteriaBuilder.isNotNull(root.get("department").get("faculty"));
            else
                return criteriaBuilder.equal(root.get("department").get("faculty").get("id"), facultyId);
        };
    }

    public Specification<Department> getSpecForDepartments(FindAllData data) {
        return Specification
                .where(findDepartmentsByFacultyCriteria(data.getFacultyId())
                        .and((Specification<Department>) getObjectByDeletedCriteria(data.getDeleted())));
    }

    private Specification<Department> findDepartmentsByFacultyCriteria(Long facultyId) {
        return (root, query, criteriaBuilder) -> {
            if (facultyId == null)
                return criteriaBuilder.isNotNull(root.get("faculty"));
            else
                return criteriaBuilder.equal(root.get("faculty").get("id"), facultyId);
        };
    }

    public Specification<TeachersJournal> getSpecForTeacherJournals(FindAllData data) {
        return Specification
                .where(findTeacherJournalsByFacultyCriteria(data.getFacultyId())
                        .and((Specification<TeachersJournal>) getObjectByDeletedCriteria(data.getDeleted())));
    }

    private Specification<TeachersJournal> findTeacherJournalsByFacultyCriteria(Long facultyId) {
        return (root, query, criteriaBuilder) -> {
            if (facultyId == null)
                return criteriaBuilder.isNotNull(root.get("teacher").get("department").get("faculty"));
            else
                return criteriaBuilder.equal(root.get("teacher").get("department").get("faculty").get("id"), facultyId);
        };
    }

}

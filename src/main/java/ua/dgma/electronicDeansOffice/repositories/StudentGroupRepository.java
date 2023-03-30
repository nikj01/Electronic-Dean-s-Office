package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, String>, JpaSpecificationExecutor {
    Optional<StudentGroup> getByName(String name);
    Optional<StudentGroup> getByGroupLeader_Uid(Long groupLeaderUid);
    Optional<StudentGroup> getByDepartment_Name(String departmentName);
    void deleteByName(String name);
    boolean existsByName(String name);
}

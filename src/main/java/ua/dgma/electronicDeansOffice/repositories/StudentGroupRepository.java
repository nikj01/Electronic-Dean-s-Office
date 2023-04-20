package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.functional.ExistByNameInterface;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long>, JpaSpecificationExecutor, ExistByNameInterface {
    Optional<StudentGroup> getByName(String name);
    Optional<List<StudentGroup>> getByNameContainingIgnoreCase(String name);
    Optional<StudentGroup> getByGroupLeader_Uid(Long groupLeaderUid);
    Optional<StudentGroup> getByStudentsContaining(Student student);
    void deleteByName(String name);
    boolean existsByName(String name);
}

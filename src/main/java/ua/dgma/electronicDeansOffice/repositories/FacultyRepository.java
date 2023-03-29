package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.Faculty;

import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, String>, JpaSpecificationExecutor {
    Optional<Faculty> getByName(String facultyName);
    void deleteByName(String name);
    boolean existsByName(String name);
}

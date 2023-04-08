package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.repositories.functional.ExistByNameInterface;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long>, JpaSpecificationExecutor, ExistByNameInterface {
    Optional<Faculty> getByName(String facultyName);
    Optional<List<Faculty>> getByNameContainingIgnoreCase(String facultyName);
    void deleteByName(String name);
    boolean existsByName(String name);
}

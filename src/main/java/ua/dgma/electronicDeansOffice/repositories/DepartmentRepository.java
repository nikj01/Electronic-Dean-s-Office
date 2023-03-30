package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.Department;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String>, JpaSpecificationExecutor {
    Optional<Department> getByName(String name);
    List<Department> getAllByFacultyName(String facultyName);
    void deleteByName(String name);
    boolean existsByName(String name);

}

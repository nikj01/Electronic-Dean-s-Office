package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.repositories.functional.ExistByNameInterface;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor, ExistByNameInterface{
    Optional<Department> getByName(String name);
    Optional<List<Department>> getByNameContainingIgnoreCase(String name);
    void deleteByName(String name);
    boolean existsByName(String name);

}

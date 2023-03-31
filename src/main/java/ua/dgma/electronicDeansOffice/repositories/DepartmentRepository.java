package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.repositories.functional.ExistByNameInterface;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String>, JpaSpecificationExecutor, ExistByNameInterface{
    Optional<Department> getById(Long id);
    Optional<Department> getByName(String name);
    List<Department> getAllByFacultyName(String facultyName);
    void deleteByName(String name);
    boolean existsByName(String name);

}

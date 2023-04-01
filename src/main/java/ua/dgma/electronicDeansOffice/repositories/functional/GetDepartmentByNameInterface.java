package ua.dgma.electronicDeansOffice.repositories.functional;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dgma.electronicDeansOffice.models.Department;

public interface GetDepartmentByNameInterface extends JpaRepository<Department, Long> {
    Department getByName(String name);
}

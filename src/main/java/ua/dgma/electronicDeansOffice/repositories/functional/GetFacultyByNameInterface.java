package ua.dgma.electronicDeansOffice.repositories.functional;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dgma.electronicDeansOffice.models.Faculty;

public interface GetFacultyByNameInterface extends JpaRepository<Faculty, Long> {
    Faculty getFacultyByName(String name);
}

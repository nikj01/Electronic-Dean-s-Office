package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

import java.util.Optional;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, String> {

    Optional<StudentGroup> getByName(String name);
}

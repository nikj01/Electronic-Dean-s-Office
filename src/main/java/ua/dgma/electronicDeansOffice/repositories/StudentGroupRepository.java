package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, String> {

    StudentGroup getByName(String name);
}

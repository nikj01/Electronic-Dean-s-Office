package ua.dgma.electronicDeansOffice.repositories.functional;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

public interface GetStudentGroupByNameInterface extends JpaRepository<StudentGroup, Long> {
    StudentGroup getStudentGroupByName(String name);
}

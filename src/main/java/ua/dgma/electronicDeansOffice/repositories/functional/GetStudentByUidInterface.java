package ua.dgma.electronicDeansOffice.repositories.functional;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dgma.electronicDeansOffice.models.Student;

public interface GetStudentByUidInterface extends JpaRepository<Student, Long> {
    Student getStudentByUid(Long uid);
}


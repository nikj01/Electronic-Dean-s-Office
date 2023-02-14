package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dgma.electronicDeansOffice.models.PersonRole;
import ua.dgma.electronicDeansOffice.models.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUid(Long uid);

    List<Student> findByRole(PersonRole personRole);

    List<Student> findStudentsByStudentGroup(String group);

    void deleteByUid(Long uid);
}

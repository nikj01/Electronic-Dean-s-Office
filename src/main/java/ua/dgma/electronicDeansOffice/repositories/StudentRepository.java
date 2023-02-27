package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.Student;

@Repository
public interface StudentRepository extends PeopleRepository<Student> {
}

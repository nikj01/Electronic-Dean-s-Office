package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.Student;

import java.util.Optional;

@Repository
public interface StudentRepository extends PeopleRepository<Student> {

}

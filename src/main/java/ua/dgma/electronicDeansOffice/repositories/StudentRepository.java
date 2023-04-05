package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.repositories.functional.ExistByNameInterface;

@Repository
public interface StudentRepository extends PeopleRepository<Student>, ExistByNameInterface {

}

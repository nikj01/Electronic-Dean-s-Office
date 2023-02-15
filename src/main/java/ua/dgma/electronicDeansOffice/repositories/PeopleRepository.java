package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.Person;

import java.util.Optional;

@Repository
public interface PeopleRepository<T extends Person> extends JpaRepository<T, Long> {

    Optional<T> getByUid(Long uid);

    Optional<T> getByEmail(String email);

    Optional<T> getBySurname(String surname);

}

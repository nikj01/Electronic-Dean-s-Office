package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository<T extends Person> extends JpaRepository<T, Long> {

    Optional<T> getByUid(Long uid);
    Optional<T> getByEmail(String email);
    List<T> getBySurname(String surname);
    void deleteByUid(Long uid);
    boolean existsByUid(Long uid);
    boolean existsBySurname(String surname);

}

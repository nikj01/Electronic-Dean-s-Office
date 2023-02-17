package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.models.PersonRole;

import java.util.Date;
import java.util.Optional;

@Repository
public interface PeopleRepository<T extends Person> extends JpaRepository<T, Long> {

    Optional<T> getByUid(Long uid);

    Optional<T> getByEmail(String email);

    Optional<T> getBySurname(String surname);

    void deleteOneByUid(Long uid);
    boolean existsByUid(Long uid);

}

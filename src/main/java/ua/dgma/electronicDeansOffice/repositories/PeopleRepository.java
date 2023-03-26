package ua.dgma.electronicDeansOffice.repositories;

//import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
//import ua.dgma.electronicDeansOffice.models.Student;
//import ua.dgma.electronicDeansOffice.models.Teacher;
//import ua.dgma.electronicDeansOffice.models.QPerson;

//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository<T extends Person> extends JpaRepository<T, Long>, JpaSpecificationExecutor {
    Optional<T> getByUid(Long uid);
    Optional<T> getByEmail(String email);
    List<T> findByDeletedIs(boolean isDeleted);
    List<T> findAll();
    List<T> getBySurname(String surname);
    void deleteByUid(Long uid);
    boolean existsByUid(Long uid);
    boolean existsBySurname(String surname);

}

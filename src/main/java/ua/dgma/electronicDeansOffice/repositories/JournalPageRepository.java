package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.JournalPage;

import java.util.List;
import java.util.Optional;

@Repository
public interface JournalPageRepository extends JpaRepository<JournalPage, Long>, JpaSpecificationExecutor {
//    Optional<JournalPage> getById(Long id);
}

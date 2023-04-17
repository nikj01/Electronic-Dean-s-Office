package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.dgma.electronicDeansOffice.models.JournalPage;

@Repository
public interface JournalPageRepository extends JpaRepository<JournalPage, Long>, JpaSpecificationExecutor {
}

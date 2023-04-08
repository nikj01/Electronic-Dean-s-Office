package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;

import java.util.Optional;

public interface TeachersJournalRepository extends JpaRepository<TeachersJournal, Long>, JpaSpecificationExecutor {
    Optional<TeachersJournal> getByTeacher_Uid(Long uid);
    Optional<TeachersJournal> getByCommentContaining(String comment);
    boolean existsById(Long uid);
}

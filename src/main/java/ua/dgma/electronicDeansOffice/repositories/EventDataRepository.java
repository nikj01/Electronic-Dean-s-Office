package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dgma.electronicDeansOffice.models.EventData;

import java.util.List;
import java.util.Optional;

public interface EventDataRepository extends JpaRepository<EventData, Long> {
    Optional<List<EventData>> getByTeachersUid(Long teacherUid);
}

package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dgma.electronicDeansOffice.models.EventData;

public interface EventDataRepository extends JpaRepository<EventData, Long> {
}

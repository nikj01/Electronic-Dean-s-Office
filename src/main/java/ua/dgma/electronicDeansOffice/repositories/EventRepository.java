package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dgma.electronicDeansOffice.models.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}

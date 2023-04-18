package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dgma.electronicDeansOffice.models.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<List<Report>> getByReportNameContainingIgnoreCase(String partOfReportName);
    Optional<List<Report>> getByEventData_EventId(Long eventId);
    Report getById(Long eventId);
}

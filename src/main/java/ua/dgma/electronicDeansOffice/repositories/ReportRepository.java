package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ua.dgma.electronicDeansOffice.models.Report;
import ua.dgma.electronicDeansOffice.models.Student;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@EnableJpaRepositories
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<List<Report>> getByReportNameContainingIgnoreCase(String partOfReportName);
    Optional<List<Report>> getByEventData_EventId(Long eventId);
    @Query(nativeQuery = true)
    Optional<Report> findById(Long reportId);
}

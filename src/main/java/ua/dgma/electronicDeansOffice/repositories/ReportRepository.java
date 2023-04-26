package ua.dgma.electronicDeansOffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ua.dgma.electronicDeansOffice.models.EventTypeEnum;
import ua.dgma.electronicDeansOffice.models.Report;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<List<Report>> getByReportNameContainingIgnoreCase(String partOfReportName);
    Optional<List<Report>> getByStudentGroup_IdAndCreatedBetween(Long groupId, LocalDateTime firstDate, LocalDateTime secondDate);
    Optional<List<Report>> getByStudentGroup_IdAndEventData_Semester(Long groupId, Integer semester);
    Optional<List<Report>> getByStudentGroup_Id(Long groupId);
    Optional<List<Report>> getByEventData_EventId(Long eventId);
    Optional<List<Report>> getByEventData_PageId(Long pageId);
    Optional<List<Report>> getByStudentGroup_IdAndEventData_EventTypeAndCreatedBetween(Long groupId, EventTypeEnum eventType, LocalDateTime searchFrom, LocalDateTime searchTo);
    Optional<List<Report>> getByStudentGroup_IdAndEventData_EventTypeAndEventData_Semester(Long groupId, EventTypeEnum eventType, Integer semester);
    Optional<List<Report>> getByStudentGroup_IdAndEventData_EventType(Long groupId, EventTypeEnum eventType);
}

package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.*;
import ua.dgma.electronicDeansOffice.repositories.EventRepository;
import ua.dgma.electronicDeansOffice.repositories.ReportRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.report.RegisterReportData;
import ua.dgma.electronicDeansOffice.services.impl.data.report.UpdateReportData;
import ua.dgma.electronicDeansOffice.services.interfaces.EventDataService;
import ua.dgma.electronicDeansOffice.services.interfaces.ReportService;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceObjectWithSuchID;

@Service
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final StudentGroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final EventRepository eventRepository;
    private final EventDataService dataService;
    private String className;
    private String eventClassName;
    private String studentGroupClassName;
    private String studentClassName;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository,
                             StudentGroupRepository groupRepository,
                             StudentRepository studentRepository,
                             EventRepository eventRepository,
                             EventDataService dataService) {
        this.reportRepository = reportRepository;
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.eventRepository = eventRepository;
        this.dataService = dataService;
        this.className = Report.class.getSimpleName();
        this.eventClassName = Event.class.getSimpleName();
        this.studentGroupClassName = StudentGroup.class.getSimpleName();
        this.studentClassName = Student.class.getSimpleName();
    }

    @Override
    public Report findOne(Long reportId) {
        return reportRepository.findById(reportId).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "id", reportId)));
    }

    @Override
    public List<Report> findByName(String reportName) {
        return reportRepository.getByReportNameContainingIgnoreCase(reportName).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "reportName", reportName)));
    }

    @Override
    public List<Report> findByEvent(Long eventId) {
        return reportRepository.getByEventData_EventId(eventId).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "event_id", eventId)));
    }

    @Override
    public List<Report> findByJournalPage(Long pageId) {
        return reportRepository.getByEventData_PageId(pageId).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "page_id", pageId)));
    }

    @Override
    @Transactional
    public void create(RegisterReportData data) {
        validateObjects(data);

        Report newReport = new Report();

        setNewReportName(newReport, data);
        setStudentAttendance(newReport, data);
        setStudentMarks(newReport, data);
        setStudentGroup(newReport, data);
        setEventData(newReport, data);
        setCreated(newReport);

        saveReport(newReport);
    }

    private void validateObjects(RegisterReportData data) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(eventClassName, getEventId(data), eventRepository));
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(studentGroupClassName, getStudentGroupId(data), groupRepository));
        checkStudents(data);
    }

    private void checkStudents(RegisterReportData data) {
        for (Long studentId : data.getStudentAttendance().keySet())
            checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(studentClassName, studentId, studentRepository));
    }

    private Long getEventId(RegisterReportData data) {
        return data.getEvent().getId();
    }

    private Long getStudentGroupId(RegisterReportData data) {
        return data.getStudentGroup().getId();
    }

    private void setNewReportName(Report report, RegisterReportData data) {
        report.setReportName(createReportName(data));
    }

    private String createReportName(RegisterReportData data) {
        return new String(getPageName(data) + " " +
                getGroupName(data) + " " +
                getEventType(data) + " " +
                getEventTDate(data));
    }

    private String getPageName(RegisterReportData data) {
        return getExistingPage(data).getPageName();
    }

    private JournalPage getExistingPage(RegisterReportData data) {
        return getExistingEvent(data).getPage();
    }

    private Event getExistingEvent(RegisterReportData data) {
        return eventRepository.findById(getEventId(data)).get();
    }

    private EventTypeEnum getEventType(RegisterReportData data) {
        return getExistingEvent(data).getEventType();
    }

    private LocalDateTime getEventTDate(RegisterReportData data) {
        return getExistingEvent(data).getDate();
    }

    private String getGroupName(RegisterReportData data) {
        return getExistingGroup(data).getName();
    }

    private StudentGroup getExistingGroup(RegisterReportData data) {
        return groupRepository.findById(getStudentGroupId(data)).get();
    }

    private void setCreated(Report report) {
        report.setCreated(LocalDateTime.now());
    }

    private void setStudentAttendance(Report report, RegisterReportData data) {
        report.setStudentAttendance(getStudentAttendance(data));
    }

    private Map<Long, Boolean> getStudentAttendance(RegisterReportData data) {
        return data.getStudentAttendance();
    }

    private void setStudentMarks(Report report, RegisterReportData data) {
        report.setStudentMarks(getStudentMarks(data));
    }

    private Map<Long, Integer> getStudentMarks(RegisterReportData data) {
        return data.getStudentMarks();
    }

    private void setStudentGroup(Report report, RegisterReportData data) {
        report.setStudentGroup(getExistingGroup(data));
    }

    private void saveReport(Report report) {
        reportRepository.save(report);
    }

    private void setEventData(Report report, RegisterReportData data) {
        report.setEventData(dataService.createNewEventData(getExistingEvent(data)));
    }

    @Override
    @Transactional
    public void update(UpdateReportData data) {
        Report existingReport = findOne(data.getReportId());
        Report updatedReport = data.getReport();

        setStudentAttendance(existingReport, updatedReport);
        setStudentMarks(existingReport, updatedReport);
        setUpdated(existingReport);

        saveReport(existingReport);
    }

    private void setStudentAttendance(Report existingReport, Report updatedReport) {
        existingReport.setStudentAttendance(getUpdatedAttendance(updatedReport));
    }

    private Map<Long, Boolean> getUpdatedAttendance(Report report) {
        return report.getStudentAttendance();
    }

    private void setStudentMarks(Report existingReport, Report updatedReport) {
        existingReport.setStudentMarks(getUpdatedMarks(updatedReport));
    }

    private Map<Long, Integer> getUpdatedMarks(Report report) {
        return report.getStudentMarks();
    }

    private void setUpdated(Report existingReport) {
        existingReport.setUpdated(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void delete(Long reportId) {
        reportRepository.deleteById(reportId);
    }

}


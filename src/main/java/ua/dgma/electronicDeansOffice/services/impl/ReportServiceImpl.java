package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.*;
import ua.dgma.electronicDeansOffice.repositories.*;
import ua.dgma.electronicDeansOffice.services.impl.data.report.RegisterReportData;
import ua.dgma.electronicDeansOffice.services.impl.data.report.UpdateReportData;
import ua.dgma.electronicDeansOffice.services.interfaces.EventDataService;
import ua.dgma.electronicDeansOffice.services.interfaces.ReportService;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;

import java.time.LocalDateTime;
import java.util.*;

import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceObjectWithSuchID;

@Service
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final StudentGroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final EventRepository eventRepository;
    private final JournalPageRepository pageRepository;
    private final EventDataService dataService;
    private String className;
    private String eventClassName;
    private String studentGroupClassName;
    private String studentClassName;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository,
                             StudentGroupRepository groupRepository,
                             StudentRepository studentRepository, EventRepository eventRepository,
                             JournalPageRepository pageRepository,
                             EventDataService dataService) {
        this.reportRepository = reportRepository;
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.eventRepository = eventRepository;
        this.pageRepository = pageRepository;
        this.dataService = dataService;
        this.className = Report.class.getSimpleName();
        this.eventClassName = Event.class.getSimpleName();
        this.studentGroupClassName = StudentGroup.class.getSimpleName();
        this.studentClassName = Student.class.getSimpleName();
    }

    @Override
    public Report findOne(Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "id", reportId)));
//        return reportRepository.findById(reportId).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "id", reportId)));
        System.out.println(report.getStudentAttendance());
        System.out.println(report.getStudentMarks());

         return report;
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
    @Transactional
    public void create(RegisterReportData data) {
        validateObjects(data);

        Report newReport = new Report();

        setNewReportName(newReport, data);
        setCreated(newReport);
        setStudentAttendance(newReport, data);
        setStudentMarks(newReport, data);
        setEventData(newReport, data);

        saveReport(newReport);
    }

    private void validateObjects(RegisterReportData data) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(eventClassName, getEventId(data), eventRepository));
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(studentGroupClassName, getStudentGroupId(data), groupRepository));
        checkStudents(data);
    }

    private Long getEventId(RegisterReportData data) {
        return data.getEvent().getId();
    }

    private Long getStudentGroupId(RegisterReportData data) {
        return data.getStudentGroup().getId();
    }

    private Long getStudentId(Student student) {
        return student.getUid();
    }

    private void checkStudents(RegisterReportData data) {
        for (Long studentId : data.getStudentAttendance().keySet())
            checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(studentClassName, studentId, studentRepository));
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

    private Map<Student, Boolean> getStudentAttendance(RegisterReportData data) {
//        return data.getStudentAttendance().keySet();

        Map<Student, Boolean> newMap = new TreeMap<>();

        for (Long id : data.getStudentAttendance().keySet())
            newMap.put(studentRepository.findById(id).get(), data.getStudentAttendance().get(id));

        return newMap;
    }

    private void setStudentMarks(Report report, RegisterReportData data) {
        report.setStudentMarks(getStudentMarks(data));
    }

    private Map<Student, Integer> getStudentMarks(RegisterReportData data) {
//        return data.getStudentMarks();

        Map<Student, Integer> newMap = new TreeMap<>();

        for (Long id : data.getStudentMarks().keySet())
            newMap.put(studentRepository.findById(id).get(), data.getStudentMarks().get(id));

        return newMap;
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

        existingReport.setStudentAttendance(updatedReport.getStudentAttendance());
        existingReport.setStudentMarks(updatedReport.getStudentMarks());

        saveReport(existingReport);
    }

    @Override
    @Transactional
    public void delete(Long reportId) {
        reportRepository.deleteById(reportId);
    }
}

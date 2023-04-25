package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.models.EventTypeEnum;
import ua.dgma.electronicDeansOffice.models.Report;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.ReportRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.student.DataForStudentStatistics;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.DataForGroupStatistics;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.interfaces.ReportsAnalyzer;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class ReportsAnalyzerImpl implements ReportsAnalyzer {
    private final ReportRepository reportRepository;
    private final StudentGroupService studentGroupService;
    private final PeopleService<Student> studentService;

    @Autowired
    public ReportsAnalyzerImpl(ReportRepository reportRepository,
                               StudentGroupService studentGroupService,
                               PeopleService<Student> studentService) {
        this.reportRepository = reportRepository;
        this.studentGroupService = studentGroupService;
        this.studentService = studentService;
    }

    @Override
    public Map<Long, Double> getAvgAttendanceForStudent(DataForStudentStatistics data) {
        Map<Long, Double> studentAttendance = new HashMap<>();
        List<Report> reports = findReportsByGroup(data);
        Student student = findStudent(data);
        double countedReports = 0;

        if (reports.size() != 0) {
            for (Report report : reports)
                if (report.getStudentAttendance().get(student.getUid()))
                    countedReports++;

            double avgAttendance = (countedReports / reports.size()) * 100;
            studentAttendance.put(getStudentId(data), avgAttendance);
        } else studentAttendance.put(getStudentId(data), 0.0);

        return studentAttendance;
    }

    private List<Report> findReportsByGroup(DataForStudentStatistics data) {
        if (getSearchFrom(data) == null)
            if (getSemester(data) == null)
                return reportRepository.getByStudentGroup_Id(getGroupId(data)).get();
            else
                return reportRepository.getByStudentGroup_IdAndEventData_Semester(getGroupId(data), getSemester(data)).get();
        else
            return reportRepository.getByStudentGroup_IdAndCreatedBetween(getGroupId(data), getSearchFrom(data), getSearchTo(data)).get();
    }

    private LocalDateTime getSearchFrom(DataForStudentStatistics data) {
        return data.getSearchFrom();
    }

    private LocalDateTime getSearchTo(DataForStudentStatistics data) {
        return data.getSearchTo();
    }

    private Long getStudentId(DataForStudentStatistics data) {
        return data.getStudentId();
    }

    private Integer getSemester(DataForStudentStatistics data) {
        return data.getSemester();
    }

    private Long getGroupId(DataForStudentStatistics data) {
        return findGroupByStudent(data).getId();
    }

    private StudentGroup findGroupByStudent(DataForStudentStatistics data) {
        return studentGroupService.findByStudent(findStudent(data));
    }

    private Student findStudent(DataForStudentStatistics data) {
        return studentService.findByUid(getStudentId(data));
    }

    @Override
    public Map<Long, Double> getAvgAttendanceForStudentsOnFaculty(FindAllData data) {
        Map<Long, Double> facultyAttendance = new HashMap<>();

        for (Long studentId : getStudentsUids(data))
            facultyAttendance.putAll(getAvgAttendanceForStudent(new DataForStudentStatistics(studentId, getSemester(data), getSearchFrom(data), getSearchTo(data))));

        return facultyAttendance;
    }

    private Set<Long> getStudentsUids(FindAllData data) {
        Set<Long> uids = new HashSet<>();

        for (Student student : findAllPeople(data)) {
            if (student.isDeleted() != true)
                uids.add(student.getUid());
        }

        return uids;
    }

    private List<Student> findAllPeople(FindAllData data) {
        return studentService.findAllPeople(data);
    }

    private Integer getSemester(FindAllData data) {
        return data.getSemester();
    }

    private LocalDateTime getSearchFrom(FindAllData data) {
        return data.getSearchFrom();
    }

    private LocalDateTime getSearchTo(FindAllData data) {
        return data.getSearchTo();
    }

    @Override
    public Map<Long, Double> getAvgAttendanceForGroup(DataForGroupStatistics data) {
        Map<Long, Double> groupAttendance = new HashMap<>();
        List<Report> reports = findReportsByGroup(data);
        Set<Long> students = getStudentsFromReports(reports);
        double totalAttendance = 0;
        double present = 0;

        if (reports.size() != 0) {
            for (Long studentId : students) {
                for (Report report : reports) {
                    Boolean attend = getAttendance(report, studentId);
                    if (attend != null && attend)
                        present++;
                }
            }

            totalAttendance += present / reports.size();
            double avgAttendance = (totalAttendance / students.size()) * 100;
            groupAttendance.put(getGroupId(data), avgAttendance);
        } else groupAttendance.put(getGroupId(data), 0.0);

        return groupAttendance;
    }

    private List<Report> findReportsByGroup(DataForGroupStatistics data) {
        if (getSearchFrom(data) == null)
            if (getSemester(data) == null)
                return reportRepository.getByStudentGroup_Id(getGroupId(data)).get();
            else
                return reportRepository.getByStudentGroup_IdAndEventData_Semester(getGroupId(data), getSemester(data)).get();
        else
            return reportRepository.getByStudentGroup_IdAndCreatedBetween(getGroupId(data), getSearchFrom(data), getSearchTo(data)).get();
    }

    private LocalDateTime getSearchFrom(DataForGroupStatistics data) {
        return data.getSearchFrom();
    }

    private LocalDateTime getSearchTo(DataForGroupStatistics data) {
        return data.getSearchTo();
    }

    private Integer getSemester(DataForGroupStatistics data) {
        return data.getSemester();
    }

    private Long getGroupId(DataForGroupStatistics data) {
        return findGroupById(data).getId();
    }

    private StudentGroup findGroupById(DataForGroupStatistics data) {
        return studentGroupService.findOne(getGroupId(data));
    }

    private Set<Long> getStudentsFromReports(List<Report> reports) {
        Set<Long> studentIds = new HashSet<>();

        for (Report report : reports)
            studentIds.addAll(report.getStudentAttendance().keySet());

        return studentIds;
    }

    private Boolean getAttendance(Report report, Long studentId) {
        return report.getStudentAttendance().get(studentId);
    }

    @Override
    public Map<Long, Double> getAvgAttendanceForGroupsOnFaculty(FindAllData data) {
        Map<Long, Double> facultyAttendance = new HashMap<>();

        for (Long groupId : getStudentGroupsId(data))
            facultyAttendance.putAll(getAvgAttendanceForGroup(new DataForGroupStatistics(groupId, getSemester(data), getSearchFrom(data), getSearchTo(data))));

        return facultyAttendance;
    }

    private Set<Long> getStudentGroupsId(FindAllData data) {
        Set<Long> ids = new HashSet<>();

        for (StudentGroup group : findAll(data)) {
            if (group.isDeleted() != true)
                ids.add(group.getId());
        }

        return ids;
    }

    private List<StudentGroup> findAll(FindAllData data) {
        return studentGroupService.findAll(data);
    }

    @Override
    public Map<Long, Double> getAvgGradeForStudent(DataForStudentStatistics data) {
        Map<Long, Double> studentAvgGrade = new HashMap<>();
        List<Report> reports = findFinals(data);
        Student student = findStudent(data);
        double totalGrade = 0;

        if (reports.size() != 0) {
            for (Report report : reports)
                totalGrade += report.getStudentMarks().get(student.getUid());

            double avgGrade = totalGrade / reports.size();
            studentAvgGrade.put(getStudentId(data), avgGrade);
        } else studentAvgGrade.put(getStudentId(data), 0.0);

        return studentAvgGrade;
    }

    private List<Report> findFinals(DataForStudentStatistics data) {
        List<Report> reports = new ArrayList<>();
        if (getSearchFrom(data) == null)
            if (getSemester(data) == null) {
                reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventType(getGroupId(data), EventTypeEnum.EXAM).get());
                reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventType(getGroupId(data), EventTypeEnum.TEST).get());
                reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventType(getGroupId(data), EventTypeEnum.COURSEWORK).get());
            } else {
                reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventTypeAndEventData_Semester(getGroupId(data), EventTypeEnum.EXAM, getSemester(data)).get());
                reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventTypeAndEventData_Semester(getGroupId(data), EventTypeEnum.TEST, getSemester(data)).get());
                reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventTypeAndEventData_Semester(getGroupId(data), EventTypeEnum.COURSEWORK, getSemester(data)).get());
            }
        else {
            reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventTypeAndCreatedBetween(getGroupId(data), EventTypeEnum.EXAM, getSearchFrom(data), getSearchTo(data)).get());
            reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventTypeAndCreatedBetween(getGroupId(data), EventTypeEnum.TEST, getSearchFrom(data), getSearchTo(data)).get());
            reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventTypeAndCreatedBetween(getGroupId(data), EventTypeEnum.COURSEWORK, getSearchFrom(data), getSearchTo(data)).get());
        }

        return reports;
    }

    @Override
    public Map<Long, Double> getAvgGradeForStudentsOnFaculty(FindAllData data) {
        Map<Long, Double> facultyAttendance = new HashMap<>();

        for (Long studentId : getStudentsUids(data))
            facultyAttendance.putAll(getAvgGradeForStudent(new DataForStudentStatistics(studentId, getSemester(data), getSearchFrom(data), getSearchTo(data))));

        return facultyAttendance;
    }

    @Override
    public Map<String, Integer> getExtractWithGradesForStudent(DataForStudentStatistics data) {
        Map<String, Integer> extractWithGrades = new HashMap<>();
        List<Report> reports = findFinals(data);
        Student student = findStudent(data);

        if (reports.size() != 0)
            for (Report report : reports)
                extractWithGrades.putAll(getFinal(report, student.getUid()));

        else extractWithGrades.put("It seems that this student has no ratings yet...", 0);

        return extractWithGrades;
    }

    private Map<String, Integer> getFinal(Report report, Long studentId) {
        Map<String, Integer> finals = new HashMap<>();

        for (Map.Entry<Long, Integer> entry : report.getStudentMarks().entrySet())
            finals.put(report.getEventData().getPageName(), entry.getValue());

        finals.put(report.getEventData().getPageName(), report.getStudentMarks().get(studentId));
        return finals;
    }
}

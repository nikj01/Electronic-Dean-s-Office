package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.extractWithGrades.Extract;
import ua.dgma.electronicDeansOffice.models.EventTypeEnum;
import ua.dgma.electronicDeansOffice.models.Report;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.ReportRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.student.DataForStudentStatistics;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.interfaces.ReportsAnalyzerForStudent;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional(readOnly = true)
public class ReportsAnalyzerForStudentImpl implements ReportsAnalyzerForStudent {
    private final ReportRepository reportRepository;
    private final StudentGroupService studentGroupService;
    private final PeopleService<Student> studentService;
    private final ExtractServiceImpl extractService;

    @Autowired
    public ReportsAnalyzerForStudentImpl(ReportRepository reportRepository,
                                         StudentGroupService studentGroupService,
                                         PeopleService<Student> studentService,
                                         ExtractServiceImpl extractService) {
        this.reportRepository = reportRepository;
        this.studentGroupService = studentGroupService;
        this.studentService = studentService;
        this.extractService = extractService;
    }

    @Override
    public Map<Long, Double> getAvgAttendanceForStudent(DataForStudentStatistics data) {
        Map<Long, Double> studentAttendance = new HashMap<>();
        List<Report> reports = findReportsByGroup(data);
        double countedReports = 0;

        if (reports.size() != 0) {
            for (Report report : reports)
                if (report.getStudentAttendance().get(getStudentId(data)))
                    countedReports++;

            double avgAttendance = (countedReports / reports.size()) * 100;
            studentAttendance.put(getStudentId(data), avgAttendance);
        } else studentAttendance.put(getStudentId(data), 0.0);

        return studentAttendance;
    }

    private List<Report> findReportsByGroup(DataForStudentStatistics data) {
        if (getSearchFrom(data) == null)
            return reportRepository.getByStudentGroup_Id(getGroupId(data)).get();
        else
            return reportRepository.getByStudentGroup_IdAndCreatedBetween(getGroupId(data), getSearchFrom(data), getSearchTo(data)).get();
    }

    private Long getGroupId(DataForStudentStatistics data) {
        return findGroupByStudent(data).getId();
    }

    private StudentGroup findGroupByStudent(DataForStudentStatistics data) { return studentGroupService.findByStudent(findStudent(data)); }

    private Student findStudent(DataForStudentStatistics data) {
        return studentService.findByUid(getStudentId(data));
    }

    private LocalDateTime getSearchFrom(DataForStudentStatistics data) {
        return data.getSearchFrom();
    }

    private LocalDateTime getSearchTo(DataForStudentStatistics data) {
        return data.getSearchTo();
    }

    private Long getStudentId(DataForStudentStatistics data) { return data.getStudentId(); }

    @Override
    public Map<Long, Double> getAvgGradeForStudent(DataForStudentStatistics data) {
        Map<Long, Double> studentAvgGrade = new HashMap<>();
        List<Report> reports =  findFinals(data);
        double totalGrade = 0;

        if (reports.size() != 0) {
            for (Report report : reports)
                totalGrade += report.getStudentMarks().get(getStudentId(data));

            double avgGrade = totalGrade / reports.size();
            studentAvgGrade.put(getStudentId(data), avgGrade);
        } else studentAvgGrade.put(getStudentId(data), 0.0);

        return studentAvgGrade;
    }

    private List<Report> findFinals(DataForStudentStatistics data) {
        List<Report> reports = new ArrayList<>();

        if (getSearchFrom(data) == null) {
            reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventType(getGroupId(data), EventTypeEnum.EXAM).get());
            reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventType(getGroupId(data), EventTypeEnum.TEST).get());
            reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventType(getGroupId(data), EventTypeEnum.COURSEWORK).get());
        }
        else {
            reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventTypeAndCreatedBetween(getGroupId(data), EventTypeEnum.EXAM, getSearchFrom(data), getSearchTo(data)).get());
            reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventTypeAndCreatedBetween(getGroupId(data), EventTypeEnum.TEST, getSearchFrom(data), getSearchTo(data)).get());
            reports.addAll(reportRepository.getByStudentGroup_IdAndEventData_EventTypeAndCreatedBetween(getGroupId(data), EventTypeEnum.COURSEWORK, getSearchFrom(data), getSearchTo(data)).get());
        }

        return reports;
    }

    @Override
    public Map<Long, Double> getAvgGradeForStudent(Long studentId, List<Report> reports) {
        Map<Long, Double> studentAvgGrade = new HashMap<>();
        double totalGrade = 0;

        if (reports.size() != 0) {
            for (Report report : reports)
                totalGrade += report.getStudentMarks().get(studentId);

            double avgGrade = totalGrade / reports.size();
            studentAvgGrade.put(studentId, avgGrade);
        } else studentAvgGrade.put(studentId, 0.0);

        return studentAvgGrade;
    }

    @Override
    public Map<Long, Double> getAvgAttendanceForStudent(Long studentId, List<Report> reports) {
        Map<Long, Double> studentAttendance = new HashMap<>();
        double countedReports = 0;

        if (reports.size() != 0) {
            for (Report report : reports)
                if (report.getStudentAttendance().get(studentId))
                    countedReports++;

            double avgAttendance = (countedReports / reports.size()) * 100;
            studentAttendance.put(studentId, avgAttendance);
        } else studentAttendance.put(studentId, 0.0);

        return studentAttendance;
    }

    @Override
    public List<Extract> getExtractWithGradesForStudent(DataForStudentStatistics data) {
        List<Extract> extracts = new ArrayList<>();
        List<Report> reports = findFinals(data);
        Student student = findStudent(data);

        if (reports.size() != 0) {
            for (Report report : reports)
                extracts.add(extractService.getExtractWithGradeForStudent(report, student.getUid()));
        } else extracts.add(new Extract());

        return extracts;
    }
}

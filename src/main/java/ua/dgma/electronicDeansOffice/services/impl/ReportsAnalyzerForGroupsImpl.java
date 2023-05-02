package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.models.Report;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.ReportRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.DataForGroupStatistics;
import ua.dgma.electronicDeansOffice.services.interfaces.ReportsAnalyzerForGroups;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Transactional(readOnly = true)
public class ReportsAnalyzerForGroupsImpl implements ReportsAnalyzerForGroups {
    private final ReportRepository reportRepository;
    private final StudentGroupService groupService;

    @Autowired
    public ReportsAnalyzerForGroupsImpl(ReportRepository reportRepository,
                                        StudentGroupService groupService) {
        this.reportRepository = reportRepository;
        this.groupService = groupService;
    }

    @Override
    public Map<Long, Double> getAvgAttendanceForGroup(DataForGroupStatistics data) {
        Map<Long, Double> groupAttendance = new HashMap<>();
        List<Report> reports = findReportsByGroup(data);
        Set<Long> students = getStudentsFromGroup(data);
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

    @Override
    public List<Report> findReportsByGroup(DataForGroupStatistics data) {
        if (getSearchFrom(data) == null)
            return reportRepository.getByStudentGroup_Id(getGroupId(data)).get();
        else
            return reportRepository.getByStudentGroup_IdAndCreatedBetween(getGroupId(data), getSearchFrom(data), getSearchTo(data)).get();
    }

    private LocalDateTime getSearchFrom(DataForGroupStatistics data) {
        return data.getSearchFrom();
    }

    private LocalDateTime getSearchTo(DataForGroupStatistics data) {
        return data.getSearchTo();
    }

    private Long getGroupId(DataForGroupStatistics data) {
        return data.getGroupId();
    }

    private Set<Long> getStudentsFromGroup(DataForGroupStatistics data) {
        StudentGroup group = groupService.findOne(getGroupId(data));
        Set<Long> studentIds = new HashSet<>();

        for (Student student: group.getStudents())
            if (student.isDeleted() != true)
                studentIds.add(student.getUid());

        return studentIds;
    }

    private Boolean getAttendance(Report report, Long studentId) {
        return report.getStudentAttendance().get(studentId);
    }
}

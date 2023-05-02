package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.models.EventTypeEnum;
import ua.dgma.electronicDeansOffice.models.Report;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.ReportRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.DataForGroupStatistics;
import ua.dgma.electronicDeansOffice.services.interfaces.ReportsAnalyzerForFaculty;
import ua.dgma.electronicDeansOffice.services.interfaces.ReportsAnalyzerForStudent;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Transactional(readOnly = true)
public class ReportsAnalyzerForFacultyImpl implements ReportsAnalyzerForFaculty {
    private final ReportRepository reportRepository;
    private final StudentGroupService studentGroupService;
    private final ReportsAnalyzerForStudent analyzerForStudent;
    private final ReportsAnalyzerForGroupsImpl analyzerForGroups;

    @Autowired
    public ReportsAnalyzerForFacultyImpl(ReportRepository reportRepository,
                                         StudentGroupService studentGroupService,
                                         ReportsAnalyzerForStudent analyzerForStudent,
                                         ReportsAnalyzerForGroupsImpl analyzerForGroups) {
        this.reportRepository = reportRepository;
        this.studentGroupService = studentGroupService;
        this.analyzerForStudent = analyzerForStudent;
        this.analyzerForGroups = analyzerForGroups;
    }

    @Override
    public Map<Long, Double> getAvgAttendanceForStudentsOnFaculty(FindAllData data) {
        Map<Long, Double> avgAttendance = new HashMap<>();

        List<StudentGroup> groups =  studentGroupService.findAll(data);
        for (StudentGroup group : groups) {
            List<Report> reports = analyzerForGroups.findReportsByGroup(new DataForGroupStatistics(group.getId(), getSearchFrom(data), getSearchTo(data)));
            for (Long studentId : getStudentsUids(group))
                avgAttendance.putAll(analyzerForStudent.getAvgAttendanceForStudent(studentId, reports));
        }

        return avgAttendance;
    }

    private Set<Long> getStudentsUids(StudentGroup group) {
        Set<Long> uids = new HashSet<>();

        for (Student student : group.getStudents()) {
            if (student.isDeleted() != true)
                uids.add(student.getUid());
        }

        return uids;
    }

    @Override
    public Map<Long, Double> getAvgAttendanceForGroupsOnFaculty(FindAllData data) {
        Map<Long, Double> facultyAttendance = new HashMap<>();

        for (Long groupId : getStudentGroupsId(data))
            facultyAttendance.putAll(analyzerForGroups.getAvgAttendanceForGroup(new DataForGroupStatistics(groupId, getSearchFrom(data), getSearchTo(data))));

        return facultyAttendance;
    }

    private Set<Long> getStudentGroupsId(FindAllData data) {
        Set<Long> ids = new HashSet<>();

        for (StudentGroup group : findAllGroups(data)) {
            if (group.isDeleted() != true)
                ids.add(group.getId());
        }

        return ids;
    }

    private List<StudentGroup> findAllGroups(FindAllData data) {
        return studentGroupService.findAll(data);
    }

    @Override
    public Map<Long, Double> getAvgGradeForStudentsOnFaculty(FindAllData data) {
        Map<Long, Double> avgGrades = new HashMap<>();

        List<StudentGroup> groups =  studentGroupService.findAll(data);
        for (StudentGroup group : groups) {
            List<Report> reports = findFinals(new DataForGroupStatistics(group.getId(), getSearchFrom(data), getSearchTo(data)));
            for (Long studentId : getStudentsUids(group))
                avgGrades.putAll(analyzerForStudent.getAvgGradeForStudent(studentId, reports));
        }

        return avgGrades;
    }

    private List<Report> findFinals(DataForGroupStatistics data) {
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

    private LocalDateTime getSearchFrom(FindAllData data) { return data.getSearchFrom(); }

    private LocalDateTime getSearchTo(FindAllData data) {
        return data.getSearchTo();
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

}

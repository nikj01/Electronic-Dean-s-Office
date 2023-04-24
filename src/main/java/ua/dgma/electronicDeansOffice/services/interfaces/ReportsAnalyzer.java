package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.student.DataForStudentStatistics;

import java.util.Map;

public interface ReportsAnalyzer {
    Map<Long, Double> getAvgAttendanceForStudent(DataForStudentStatistics data);
    Map<Long, Double> getAvgAttendanceForStudentsOnFaculty(FindAllData data);
    Map<Long, Double> getAvgAttendanceForGroup(Long groupId);
    Map<Long, Double> getAvgAttendanceForGroupsOnFaculty(FindAllData data);
    Map<Long, Double> getAvgGradeForStudent(DataForStudentStatistics data);
}

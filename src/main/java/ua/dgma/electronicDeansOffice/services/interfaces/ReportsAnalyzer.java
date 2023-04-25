package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.student.DataForStudentStatistics;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.DataForGroupStatistics;

import java.util.Map;

public interface ReportsAnalyzer {
    Map<Long, Double> getAvgAttendanceForStudent(DataForStudentStatistics data);
    Map<Long, Double> getAvgAttendanceForStudentsOnFaculty(FindAllData data);
    Map<Long, Double> getAvgGradeForStudent(DataForStudentStatistics data);
    Map<Long, Double> getAvgGradeForStudentsOnFaculty(FindAllData data);
    Map<String, Integer> getExtractWithGradesForStudent(DataForStudentStatistics data);
    Map<Long, Double> getAvgAttendanceForGroup(DataForGroupStatistics data);
    Map<Long, Double> getAvgAttendanceForGroupsOnFaculty(FindAllData data);
}

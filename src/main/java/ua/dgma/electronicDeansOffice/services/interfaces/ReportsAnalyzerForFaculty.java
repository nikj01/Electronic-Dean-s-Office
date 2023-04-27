package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Report;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.student.DataForStudentStatistics;

import java.util.List;
import java.util.Map;

public interface ReportsAnalyzerForFaculty {
    Map<Long, Double> getAvgAttendanceForStudentsOnFaculty(FindAllData data);
    Map<Long, Double> getAvgGradeForStudentsOnFaculty(FindAllData data);
    Map<Long, Double> getAvgAttendanceForGroupsOnFaculty(FindAllData data);
}

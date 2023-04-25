package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;

import java.util.Map;

public interface ReportsAnalyzerForFaculty {
    Map<Long, Double> getAvgAttendanceForStudentsOnFaculty(FindAllData data);
    Map<Long, Double> getAvgGradeForStudentsOnFaculty(FindAllData data);
    Map<Long, Double> getAvgAttendanceForGroupsOnFaculty(FindAllData data);
}

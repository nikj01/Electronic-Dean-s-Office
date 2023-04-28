package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Report;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.DataForGroupStatistics;

import java.util.List;
import java.util.Map;

public interface ReportsAnalyzerForGroups {
    Map<Long, Double> getAvgAttendanceForGroup(DataForGroupStatistics data);
    List<Report> findReportsByGroup(DataForGroupStatistics data);
}

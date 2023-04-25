package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.DataForGroupStatistics;

import java.util.Map;

public interface ReportsAnalyzerForGroups {
    Map<Long, Double> getAvgAttendanceForGroup(DataForGroupStatistics data);
}

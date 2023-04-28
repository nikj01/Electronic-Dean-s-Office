package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.mapstruct.dtos.extractWithGrades.Extract;
import ua.dgma.electronicDeansOffice.models.Report;
import ua.dgma.electronicDeansOffice.services.impl.data.student.DataForStudentStatistics;

import java.util.List;
import java.util.Map;

public interface ReportsAnalyzerForStudent {
    Map<Long, Double> getAvgAttendanceForStudent(DataForStudentStatistics data);
    Map<Long, Double> getAvgAttendanceForStudent(Long studentId, List<Report> reports);
    Map<Long, Double> getAvgGradeForStudent(DataForStudentStatistics data);
    Map<Long, Double> getAvgGradeForStudent(Long studentId, List<Report> reports);
    List<Extract> getExtractWithGradesForStudent(DataForStudentStatistics data);


}

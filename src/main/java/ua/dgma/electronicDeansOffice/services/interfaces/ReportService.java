package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Report;
import ua.dgma.electronicDeansOffice.services.impl.data.report.RegisterReportData;
import ua.dgma.electronicDeansOffice.services.impl.data.report.UpdateReportData;

import java.util.List;

public interface ReportService {
    Report findOne(Long reportId);
    List<Report> findByName(String reportName);
    List<Report> findByEvent(Long eventId);
    void create(RegisterReportData data);
    void update(UpdateReportData data);
    void delete(Long reportId);
}
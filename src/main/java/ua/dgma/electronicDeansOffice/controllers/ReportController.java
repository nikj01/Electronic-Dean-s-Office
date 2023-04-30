package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.report.ReportGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.report.ReportPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.report.ReportPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.report.ReportSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.ReportListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.ReportMapper;
import ua.dgma.electronicDeansOffice.models.Report;
import ua.dgma.electronicDeansOffice.security.annotations.*;
import ua.dgma.electronicDeansOffice.services.impl.data.report.RegisterReportData;
import ua.dgma.electronicDeansOffice.services.impl.data.report.UpdateReportData;
import ua.dgma.electronicDeansOffice.services.interfaces.ReportService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;
    private final ReportMapper reportMapper;
    private final ReportListMapper reportListMapper;

    @Autowired
    public ReportController(ReportService reportService,
                            ReportMapper reportMapper,
                            ReportListMapper reportListMapper) {
        this.reportService = reportService;
        this.reportMapper = reportMapper;
        this.reportListMapper = reportListMapper;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @AllPerople
    public ReportGetDTO findReportById(@PathVariable("id") Long reportId) {
        return reportMapper.toReportGetDTO(reportService.findOne(reportId));
    }

    @GetMapping("names/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    @AllPerople
    public List<ReportSlimGetDTO> findReportsByName(@PathVariable("name") String reportName) {
        return reportListMapper.toReportsSlimGetDTO(reportService.findByName(reportName));
    }

    @GetMapping("/events/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @AllPerople
    public List<ReportSlimGetDTO> findReportsByEvent(@PathVariable("id") Long eventId) {
        return reportListMapper.toReportsSlimGetDTO(reportService.findByEvent(eventId));
    }

    @GetMapping("/pages/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @AllButOfStudents
    public List<ReportSlimGetDTO> findReportsByPage(@PathVariable("id") Long pageId) {
        return reportListMapper.toReportsSlimGetDTO(reportService.findByJournalPage(pageId));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @IsRoot
    @IsAdmin
    @IsTeacher
    public void createReport(@RequestBody @Valid ReportPostDTO reportPostDTO) {
        RegisterReportData data = reportMapper.toReport(reportPostDTO);

        reportService.create(data);
    }

    @PatchMapping("{id}/update")
    @ResponseStatus(HttpStatus.OK)
    @IsRoot
    @IsAdmin
    @IsTeacher
    public void updateReport(@PathVariable("id") Long reportId,
                             @RequestBody @Valid ReportPatchDTO reportPatchDTO) {
        Report updatedReport = reportMapper.toReport(reportPatchDTO);

        reportService.update(new UpdateReportData(reportId, updatedReport));
    }

    @DeleteMapping("{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    @IsRoot
    @IsAdmin
    @IsTeacher
    public void deleteReport(@PathVariable("id") Long reportId) {
        reportService.delete(reportId);
    }
}

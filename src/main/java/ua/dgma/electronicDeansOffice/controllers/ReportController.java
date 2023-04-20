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
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.services.impl.data.report.RegisterReportData;
import ua.dgma.electronicDeansOffice.services.impl.data.report.UpdateReportData;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.interfaces.ReportService;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;
    private final PeopleService<Student> peopleService;
    private final StudentGroupService groupService;
    private final ReportMapper reportMapper;
    private final ReportListMapper reportListMapper;
    private Date date;

    @Autowired
    public ReportController(ReportService reportService,
                            PeopleService<Student> peopleService,
                            StudentGroupService groupService, ReportMapper reportMapper,
                            ReportListMapper reportListMapper) {
        this.reportService = reportService;
        this.peopleService = peopleService;
        this.groupService = groupService;
        this.reportMapper = reportMapper;
        this.reportListMapper = reportListMapper;
        this.date = new Date();
    }

    @GetMapping("/findById")
    @ResponseStatus(HttpStatus.FOUND)
    public ReportGetDTO findReportById(@RequestParam("id") Long reportId) {
        return reportMapper.toReportGetDTO(reportService.findOne(reportId));
    }

    @GetMapping("/findByName")
    @ResponseStatus(HttpStatus.FOUND)
    public List<ReportSlimGetDTO> findReportsByName(@RequestParam("name") String reportName) {
        return reportListMapper.toReportsSlimGetDTO(reportService.findByName(reportName));
    }

    @GetMapping("/findByEvent")
    @ResponseStatus(HttpStatus.FOUND)
    public List<ReportSlimGetDTO> findReportsByEvent(@RequestParam("id") Long eventId) {
        return reportListMapper.toReportsSlimGetDTO(reportService.findByEvent(eventId));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReport(@RequestBody @Valid ReportPostDTO reportPostDTO) {
        RegisterReportData data = reportMapper.toReport(reportPostDTO);

        reportService.create(data);
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateReport(@RequestParam("id") Long reportId,
                             @RequestBody @Valid ReportPatchDTO reportPatchDTO) {
        Report updatedReport = reportMapper.toReport(reportPatchDTO);

        reportService.update(new UpdateReportData(reportId, updatedReport));
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReport(@RequestParam("id") Long reportId) {
        reportService.delete(reportId);
    }
}

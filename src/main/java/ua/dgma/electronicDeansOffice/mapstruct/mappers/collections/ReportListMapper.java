package ua.dgma.electronicDeansOffice.mapstruct.mappers.collections;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.report.ReportSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.ReportMapper;
import ua.dgma.electronicDeansOffice.models.Report;

import java.util.List;

@Mapper(componentModel = "spring", uses = ReportMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReportListMapper {
    List<ReportSlimGetDTO> toReportsSlimGetDTO(List<Report> reports);
}

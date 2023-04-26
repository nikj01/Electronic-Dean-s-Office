package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.report.ReportGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.report.ReportPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.report.ReportPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.report.ReportSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.Report;
import ua.dgma.electronicDeansOffice.services.impl.data.report.RegisterReportData;

@Mapper(componentModel = "spring",
        uses = {EventDataMapper.class, StudentMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ReportMapper {
    @Named("postReport")
    RegisterReportData toReport(ReportPostDTO data);
    @Named("patchReport")
    Report toReport(ReportPatchDTO reportPatchDTO);
    @Named("report")
    ReportGetDTO toReportGetDTO(Report report);
    @Named("slimReport")
    ReportSlimGetDTO toReportSlimGetDTO(Report report);
}

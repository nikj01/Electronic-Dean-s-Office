package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.DeaneryWorkerGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.DeaneryWorkerPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.DeaneryWorkerPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.DeaneryWorkerSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DeaneryWorkerMapper {
    @Named("postWorker")
    DeaneryWorker toDeaneryWorker(DeaneryWorkerPostDTO deaneryWorkerPostDTO);
    @Named("patchWorker")
    DeaneryWorker toDeaneryWorker(DeaneryWorkerPatchDTO deaneryWorkerPatchDTO);
    @Named("deaneryWorker")
    DeaneryWorkerGetDTO toDeaneryWorkerGetDTO(DeaneryWorker deaneryWorker);
    @Named("slimDeaneryWorker")
    DeaneryWorkerSlimGetDTO toDeaneryWorkerSlimGetDTO(DeaneryWorker deaneryWorker);

}

package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.*;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;

import java.util.List;

@Mapper(componentModel = "spring", uses = FacultyMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DeaneryWorkerMapper {
    @Named(value = "postWorker")
    DeaneryWorker toDeaneryWorker(DeaneryWorkerPostDTO deaneryWorkerPost);
    @Named(value = "patchWorker")
    DeaneryWorker toDeaneryWorker(DeaneryWorkerPatchDTO deaneryWorkerPatch);
    DeaneryWorkerGetDTO toDeaneryWorkerGetDTO(DeaneryWorker deaneryWorker);
    DeaneryWorkerSlimGetDTO toDeaneryWorkerSlimGetDTO(DeaneryWorker deaneryWorker);

}

package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.*;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;

import java.util.List;

@Mapper(componentModel = "spring", uses = FacultyMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DeaneryWorkerMapper {

    DeaneryWorker toDeaneryWorker(DeaneryWorkerPostDTO deaneryWorkerPost);
    DeaneryWorkerGetDTO toDeaneryWorkerGetDTO(DeaneryWorker deaneryWorker);
    DeaneryWorkerSlimGetDTO toDeaneryWorkerSlimGetDTO(DeaneryWorker deaneryWorker);

}

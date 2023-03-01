package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.*;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;

import java.util.List;

public interface DeaneryWorkerMapper {

    DeaneryWorker convertToDeaneryWorker(DeaneryWorkerPostDTO deaneryWorkerPost);

    DeaneryWorkerGetDTO convertToDeaneryWorkerGetDTO(DeaneryWorker deaneryWorker);

    DeaneryWorkerSlimGetDTO convertToDeaneryWorkerSlimGetDTO(DeaneryWorker deaneryWorker);

    DeaneryWorkersGetDTO convertToDeaneryWorkersGetDTO(List<DeaneryWorker> deaneryWorkers);

    DeaneryWorkersSlimGetDTO convertToDeaneryWorkersSlimGetDTO(List<DeaneryWorker> deaneryWorkers);
}

package ua.dgma.electronicDeansOffice.mapstruct.mappers.collections;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.DeaneryWorkerGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.DeaneryWorkerSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.DeaneryWorkerMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.FacultyMapper;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;

import java.util.List;

@Mapper(componentModel = "spring", uses = DeaneryWorkerMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DeaneryWorkerListMapper {

    List<DeaneryWorkerGetDTO> toDeaneryWorkersGetDTO(List<DeaneryWorker> deaneryWorkers);
    List<DeaneryWorkerSlimGetDTO> toDeaneryWorkersSlimGetDTO(List<DeaneryWorker> deaneryWorkers);
}

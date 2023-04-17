package ua.dgma.electronicDeansOffice.mapstruct.mappers.collections;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.DeaneryWorkerSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.DeaneryWorkerMapper;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;

import java.util.List;

@Mapper(componentModel = "spring", uses = DeaneryWorkerMapper.class , injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DeaneryWorkerListMapper {
    @IterableMapping(qualifiedByName = "slimDeaneryWorker")
    List<DeaneryWorkerSlimGetDTO> toDeaneryWorkersSlimGetDTO(List<DeaneryWorker> deaneryWorkers);
}

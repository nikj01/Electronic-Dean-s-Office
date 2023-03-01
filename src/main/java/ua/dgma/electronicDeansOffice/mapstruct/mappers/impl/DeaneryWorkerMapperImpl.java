package ua.dgma.electronicDeansOffice.mapstruct.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.DeaneryWorkerMapper;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeaneryWorkerMapperImpl implements DeaneryWorkerMapper {

    private final ModelMapper mapper;

    @Autowired
    public DeaneryWorkerMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public DeaneryWorker convertToDeaneryWorker(DeaneryWorkerPostDTO deaneryWorkerPost) {
        return mapper.map(deaneryWorkerPost, DeaneryWorker.class);
    }

    @Override
    public DeaneryWorkerGetDTO convertToDeaneryWorkerGetDTO(DeaneryWorker deaneryWorker) {
        return mapper.map(deaneryWorker, DeaneryWorkerGetDTO.class);
    }

    @Override
    public DeaneryWorkerSlimGetDTO convertToDeaneryWorkerSlimGetDTO(DeaneryWorker deaneryWorker) {
        return mapper.map(deaneryWorker, DeaneryWorkerSlimGetDTO.class);
    }

    @Override
    public DeaneryWorkersGetDTO convertToDeaneryWorkersGetDTO(List<DeaneryWorker> deaneryWorkers) {
        return new DeaneryWorkersGetDTO(deaneryWorkers.stream()
                                                      .map(this::convertToDeaneryWorkerGetDTO)
                                                      .sorted()
                                                      .collect(Collectors.toList()));
    }

    @Override
    public DeaneryWorkersSlimGetDTO convertToDeaneryWorkersSlimGetDTO(List<DeaneryWorker> deaneryWorkers) {
        return new DeaneryWorkersSlimGetDTO(deaneryWorkers.stream()
                                                          .map(this::convertToDeaneryWorkerSlimGetDTO)
                                                          .sorted()
                                                          .collect(Collectors.toList()));
    }
}

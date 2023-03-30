package ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker;

import lombok.*;

import java.util.List;

@Data
public class DeaneryWorkersSlimGetDTO {

    private List<DeaneryWorkerSlimGetDTO> deaneryWorkers;
}

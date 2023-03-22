package ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker;

import lombok.*;

import java.util.List;

@Data
public class DeaneryWorkersGetDTO {

    private List<DeaneryWorkerGetDTO> deaneryWorkers;
}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class DeaneryWorkersSlimGetDTO {

    @NonNull
    private List<DeaneryWorkerSlimGetDTO> deaneryWorkers;
}
package ua.dgma.electronicDeansOffice.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;

@Component
public interface DeaneryWorkerSpecifications extends PeopleSpecifications, DeletedSpecification {
    Specification<DeaneryWorker> getSpecForDeaneryWorkers(FindAllData data);
}

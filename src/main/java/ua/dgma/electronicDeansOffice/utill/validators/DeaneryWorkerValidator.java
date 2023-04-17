package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.repositories.DeaneryWorkerRepository;

import java.util.Optional;

@Component
public class DeaneryWorkerValidator implements Validator {
    private final DeaneryWorkerRepository deaneryWorkerRepository;

    @Autowired
    public DeaneryWorkerValidator(DeaneryWorkerRepository deaneryWorkerRepository) {
        this.deaneryWorkerRepository = deaneryWorkerRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return DeaneryWorker.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DeaneryWorker worker = (DeaneryWorker) target;

        if (findWorker(worker).isPresent())
            errors.rejectValue("uid", "Deanery worker with UID " + getWorkerId(worker) + " already exists!");
    }

    private Optional<DeaneryWorker> findWorker(DeaneryWorker worker) {
        return deaneryWorkerRepository.getByUid(getWorkerId(worker));
    }

    private Long getWorkerId(DeaneryWorker worker) {
        return worker.getUid();
    }
}

package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.repositories.DeaneryWorkerRepository;

import static ua.dgma.electronicDeansOffice.utill.check.CheckExistenceFaculty.checkExistenceFaculty;

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
        DeaneryWorker deaneryWorker = (DeaneryWorker) target;

        if(deaneryWorkerRepository.getByUid(deaneryWorker.getUid()).isPresent())
            errors.rejectValue("uid", "Deanery worker with UID " + deaneryWorker.getUid() + " already exists!");
        if(deaneryWorkerRepository.getByEmail(deaneryWorker.getEmail()).isPresent())
            errors.rejectValue("email", "Deanery worker with EMAIL " + deaneryWorker.getEmail() + " already exists!");

        checkExistenceFaculty(deaneryWorker.getFaculty(), errors);
    }
}

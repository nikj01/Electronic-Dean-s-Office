package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.people.ExceptionData;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.repositories.DeaneryWorkerRepository;
import ua.dgma.electronicDeansOffice.utill.validators.DeaneryWorkerValidator;

@Service
@Transactional(readOnly = true)
public class DeaneryWorkerService extends PeopleServiceImpl<DeaneryWorker>{

    private final DeaneryWorkerRepository deaneryWorkerRepository;

    @Autowired
    protected DeaneryWorkerService(DeaneryWorkerRepository deaneryWorkerRepository,
                                   DeaneryWorkerValidator validator,
                                   ExceptionData exceptionData) {
        super(deaneryWorkerRepository, validator, exceptionData);
        this.deaneryWorkerRepository = deaneryWorkerRepository;
    }

    @Override
    public void updateByUid(Long uid, DeaneryWorker updatedDeaneryWorker, BindingResult bindingResult) {
        checkExistsWithSuchUid(uid);
        validatePerson(updatedDeaneryWorker, bindingResult);

        updatedDeaneryWorker.setUid(uid);
        deaneryWorkerRepository.save(updatedDeaneryWorker);

    }
}

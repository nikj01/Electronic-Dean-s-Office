package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.exceptions.ExceptionData;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.repositories.DeaneryWorkerRepository;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.DeaneryWorkerValidator;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;

@Service
@Transactional(readOnly = true)
public class DeaneryWorkerServiceImpl extends PeopleServiceImpl<DeaneryWorker>{

    private final DeaneryWorkerRepository deaneryWorkerRepository;
    private final DeaneryWorkerValidator deaneryWorkerValidator;

    @Autowired
    protected DeaneryWorkerServiceImpl(DeaneryWorkerRepository deaneryWorkerRepository,
                                       DeaneryWorkerValidator deaneryWorkerValidator,
                                       ExceptionData exceptionData) {
        super(deaneryWorkerRepository, deaneryWorkerValidator, exceptionData);
        this.deaneryWorkerRepository = deaneryWorkerRepository;
        this.deaneryWorkerValidator = deaneryWorkerValidator;
    }

    @Override
    public void updateByUid(Long uid, DeaneryWorker updatedDeaneryWorker, BindingResult bindingResult) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(DeaneryWorker.class.getSimpleName(), uid, deaneryWorkerRepository));
        validateObject(new ValidationData<>(deaneryWorkerValidator, updatedDeaneryWorker, bindingResult));

        updatedDeaneryWorker.setUid(uid);
        deaneryWorkerRepository.save(updatedDeaneryWorker);

    }
}

package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.ExceptionData;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
//import ua.dgma.electronicDeansOffice.models.QDeaneryWorker;
//import ua.dgma.electronicDeansOffice.models.QPerson;
import ua.dgma.electronicDeansOffice.repositories.DeaneryWorkerRepository;
import ua.dgma.electronicDeansOffice.services.specifications.Specifications;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.DeaneryWorkerValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkPaginationParameters;

@Service
@Transactional(readOnly = true)
public class DeaneryWorkerServiceImpl extends PeopleServiceImpl<DeaneryWorker>{

    private final DeaneryWorkerRepository deaneryWorkerRepository;
    private final DeaneryWorkerValidator deaneryWorkerValidator;
    private final Specifications<DeaneryWorker> specifications;

    @Autowired
    protected DeaneryWorkerServiceImpl(DeaneryWorkerRepository deaneryWorkerRepository,
                                       DeaneryWorkerValidator deaneryWorkerValidator,
                                       ExceptionData exceptionData,
                                       Specifications<DeaneryWorker> specifications) {
        super(deaneryWorkerRepository, deaneryWorkerValidator, exceptionData, specifications);
        this.deaneryWorkerRepository = deaneryWorkerRepository;
        this.deaneryWorkerValidator = deaneryWorkerValidator;
        this.specifications = specifications;
    }

    @Override
    public void updateByUid(Long uid, DeaneryWorker updatedDeaneryWorker, BindingResult bindingResult) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(DeaneryWorker.class.getSimpleName(), uid, deaneryWorkerRepository));
        validateObject(new ValidationData<>(deaneryWorkerValidator, updatedDeaneryWorker, bindingResult));

        updatedDeaneryWorker.setUid(uid);
        deaneryWorkerRepository.save(updatedDeaneryWorker);

    }

    @Override
    public void deleteByUId(Long uid) {
        deaneryWorkerRepository.deleteByUid(uid);
    }

}

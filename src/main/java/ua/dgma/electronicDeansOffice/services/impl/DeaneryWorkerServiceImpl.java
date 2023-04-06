package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.repositories.DeaneryWorkerRepository;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.services.specifications.DeaneryWorkerSpecifications;
import ua.dgma.electronicDeansOffice.services.specifications.impl.SpecificationsImpl;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByNameData;
import ua.dgma.electronicDeansOffice.utill.validators.DeaneryWorkerValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.*;

@Service
@Transactional(readOnly = true)
public class DeaneryWorkerServiceImpl extends PeopleServiceImpl<DeaneryWorker>{

    private final DeaneryWorkerRepository deaneryWorkerRepository;
    private final FacultyRepository facultyRepository;
    private final DeaneryWorkerValidator deaneryWorkerValidator;
    private final DeaneryWorkerSpecifications specifications;

    @Autowired
    protected DeaneryWorkerServiceImpl(DeaneryWorkerRepository deaneryWorkerRepository,
                                       DeaneryWorkerValidator deaneryWorkerValidator,
                                       ExceptionData exceptionData,
                                       FacultyRepository facultyRepository,
                                       DeaneryWorkerSpecifications specifications) {
        super(deaneryWorkerRepository, deaneryWorkerValidator, exceptionData, specifications);
        this.deaneryWorkerRepository = deaneryWorkerRepository;
        this.deaneryWorkerValidator = deaneryWorkerValidator;
        this.facultyRepository = facultyRepository;
        this.specifications = specifications;
    }

    @Override
    public List<DeaneryWorker> findAllWithPaginationOrWithoutByFaculty(Integer page, Integer peoplePerPage, Boolean isDeleted, String facultyName) {
        if(checkPaginationParameters(page, peoplePerPage))
            return deaneryWorkerRepository.findAll(Specification.where(specifications.findDeaneryWorkersByFacultyCriteria(facultyName).and(specifications.getObjectByDeletedCriteria(isDeleted))));
        else
            return deaneryWorkerRepository.findAll(Specification.where(specifications.findDeaneryWorkersByFacultyCriteria(facultyName).and(specifications.getObjectByDeletedCriteria(isDeleted))), PageRequest.of(page, peoplePerPage)).getContent();
    }

    @Override
    public void registerNew(DeaneryWorker deaneryWorker, BindingResult bindingResult) {
        validateObject(new ValidationData<>(deaneryWorkerValidator, deaneryWorker, bindingResult));
        checkExistsWithSuchName(new CheckExistsByNameData<>(Faculty.class.getSimpleName(), deaneryWorker.getFaculty().getName(), facultyRepository));

        deaneryWorker.setFaculty(facultyRepository.getByName(deaneryWorker.getFaculty().getName()).get());

        deaneryWorkerRepository.save(deaneryWorker);
    }

    @Override
    public void updateByUid(Long uid, DeaneryWorker updatedDeaneryWorker, BindingResult bindingResult) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(DeaneryWorker.class.getSimpleName(), uid, deaneryWorkerRepository));
        validateObject(new ValidationData<>(deaneryWorkerValidator, updatedDeaneryWorker, bindingResult));

        updatedDeaneryWorker.setUid(uid);
        updatedDeaneryWorker.setFaculty(facultyRepository.getByName(updatedDeaneryWorker.getFaculty().getName()).get());

        deaneryWorkerRepository.save(updatedDeaneryWorker);
    }

    @Override
    public void deleteByUId(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(DeaneryWorker.class.getSimpleName(), uid, deaneryWorkerRepository));

        deaneryWorkerRepository.deleteByUid(uid);
    }

}

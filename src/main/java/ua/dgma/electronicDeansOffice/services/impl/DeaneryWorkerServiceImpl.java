package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.repositories.DeaneryWorkerRepository;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;
import ua.dgma.electronicDeansOffice.services.specifications.DeaneryWorkerSpecifications;
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
    private String className;

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
        this.className = DeaneryWorker.class.getSimpleName();
    }

    @Override
    public List<DeaneryWorker> findAllWithPaginationOrWithoutByFaculty(FindAllData data) {
        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return deaneryWorkerRepository.findAll(Specification.where(specifications.findDeaneryWorkersByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))));
        else
            return deaneryWorkerRepository.findAll(Specification.where(specifications.findDeaneryWorkersByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted()))), PageRequest.of(data.getPage(), data.getObjectsPerPage())).getContent();
    }

    @Override
    public void registerNew(RegisterPersonData<DeaneryWorker> data) {
        validateObject(new ValidationData<>(deaneryWorkerValidator, data.getNewPerson(), data.getBindingResult()));
        checkExistsWithSuchName(new CheckExistsByNameData<>(className, data.getNewPerson().getFaculty().getName(), facultyRepository));

        DeaneryWorker newDeaneryWorker = data.getNewPerson();
        newDeaneryWorker.setFaculty(facultyRepository.getByName(newDeaneryWorker.getFaculty().getName()).get());

        deaneryWorkerRepository.save(newDeaneryWorker);
    }

    @Override
    public void updateByUid(UpdatePersonData<DeaneryWorker> data) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, data.getUid(), deaneryWorkerRepository));
        validateObject(new ValidationData<>(deaneryWorkerValidator, data.getUpdatedPerson(), data.getBindingResult()));

        DeaneryWorker updatedDeaneryWorker = data.getUpdatedPerson();
        updatedDeaneryWorker.setUid(data.getUid());
        updatedDeaneryWorker.setFaculty(facultyRepository.getByName(updatedDeaneryWorker.getFaculty().getName()).get());

        deaneryWorkerRepository.save(updatedDeaneryWorker);
    }

    @Override
    public void deleteByUId(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, uid, deaneryWorkerRepository));

        deaneryWorkerRepository.deleteByUid(uid);
    }

    @Override
    public void softDeleteByUId(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, uid, deaneryWorkerRepository));

        DeaneryWorker deaneryWorker = findByUid(uid);
        deaneryWorker.setDeleted(true);

        deaneryWorkerRepository.save(deaneryWorker);
    }

}

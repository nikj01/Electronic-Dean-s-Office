package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.models.Faculty;
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
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceObjectWithSuchID;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceObjectWithSuchName;

@Service
@Transactional(readOnly = true)
public class DeaneryWorkerServiceImpl extends PeopleServiceImpl<DeaneryWorker> {
    private final DeaneryWorkerRepository deaneryWorkerRepository;
    private final FacultyRepository facultyRepository;
    private final DeaneryWorkerValidator deaneryWorkerValidator;
    private final DeaneryWorkerSpecifications specifications;
    private String className;

    @Autowired
    protected DeaneryWorkerServiceImpl(DeaneryWorkerRepository deaneryWorkerRepository,
                                       DeaneryWorkerValidator deaneryWorkerValidator,
                                       FacultyRepository facultyRepository,
                                       DeaneryWorkerSpecifications specifications) {
        super(deaneryWorkerRepository, specifications);
        this.deaneryWorkerRepository = deaneryWorkerRepository;
        this.deaneryWorkerValidator = deaneryWorkerValidator;
        this.facultyRepository = facultyRepository;
        this.specifications = specifications;
        this.className = DeaneryWorker.class.getSimpleName();
    }

    @Override
    protected Specification getSpec(FindAllData data) {
        return Specification.where(specifications.findDeaneryWorkersByFacultyCriteria(data.getFacultyId()).and(specifications.getObjectByDeletedCriteria(data.getDeleted())));
    }

    @Override
    public void register(RegisterPersonData<DeaneryWorker> data) {
        checkExistenceObjectWithSuchName(new CheckExistsByNameData<>(className, getFacultyId(data), facultyRepository));
        validateObject(new ValidationData<>(deaneryWorkerValidator, data.getNewPerson(), data.getBindingResult()));

        DeaneryWorker newDeaneryWorker = data.getNewPerson();

        setFacultyForDeaneryWorker(newDeaneryWorker);

        savePerson(newDeaneryWorker);
    }

    private String getFacultyId(RegisterPersonData<DeaneryWorker> data) {
        return data.getNewPerson().getFaculty().getName();
    }

    private void setFacultyForDeaneryWorker(DeaneryWorker deaneryWorker) {
        deaneryWorker.setFaculty(getExistingFaculty(deaneryWorker));
    }

    private Faculty getExistingFaculty(DeaneryWorker deaneryWorker) {
        return facultyRepository.findById(getFacultyId(deaneryWorker)).get();
    }

    private Long getFacultyId(DeaneryWorker deaneryWorker) {
        return deaneryWorker.getFaculty().getId();
    }

    @Override
    public void update(UpdatePersonData<DeaneryWorker> data) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, data.getUid(), deaneryWorkerRepository));
        validateObject(new ValidationData<>(deaneryWorkerValidator, data.getUpdatedPerson(), data.getBindingResult()));

        DeaneryWorker updatedDeaneryWorker = data.getUpdatedPerson();

        setIdInUpdatedPerson(updatedDeaneryWorker, data);
        setFacultyForDeaneryWorker(updatedDeaneryWorker);

        savePerson(updatedDeaneryWorker);
    }

    @Override
    public void delete(Long uid) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, uid, deaneryWorkerRepository));

        deaneryWorkerRepository.deleteByUid(uid);
    }

    @Override
    public void softDelete(Long uid) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, uid, deaneryWorkerRepository));

        savePerson(markPersonAsDeleted(findByUid(uid)));
    }

    @Override
    public void softDeletePeople(List<DeaneryWorker> people) {
        for (DeaneryWorker worker : people)
            softDelete(worker.getUid());
    }
}

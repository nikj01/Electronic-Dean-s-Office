package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.faculty.RegisterFacultyData;
import ua.dgma.electronicDeansOffice.services.impl.data.faculty.UpdateFacultyData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.interfaces.DepartmentService;
import ua.dgma.electronicDeansOffice.services.interfaces.FacultyService;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.specifications.DeletedSpecification;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByNameData;
import ua.dgma.electronicDeansOffice.utill.validators.AbstractValidator;
import ua.dgma.electronicDeansOffice.utill.validators.data.DataForAbstractValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.*;

@Service
@Transactional(readOnly = true)
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final PeopleService<DeaneryWorker> deaneryWorkerService;
    private final DepartmentService departmentService;
    private final AbstractValidator facultyValidator;
    private final DeletedSpecification<Faculty> specification;
    private String className;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository,
                              PeopleService<DeaneryWorker> deaneryWorkerService,
                              DepartmentService departmentService,
                              AbstractValidator facultyValidator,
                              DeletedSpecification<Faculty> specification) {
        this.facultyRepository = facultyRepository;
        this.deaneryWorkerService = deaneryWorkerService;
        this.departmentService = departmentService;
        this.facultyValidator = facultyValidator;
        this.specification = specification;
        className = Faculty.class.getSimpleName();
    }

    @Override
    public Faculty findOne(Long facultyId) {
        return facultyRepository.findById(facultyId).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "id", facultyId)));
    }

    @Override
    public List<Faculty> findByName(String facultyName) {
        return facultyRepository.getByNameContainingIgnoreCase(facultyName).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "name", facultyName)));
    }

    @Override
    public List<Faculty> findAll(FindAllData data) {
        if (checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return findAllWithSpec(data);
        else
            return findAllWithSpecAndPagination(data);
    }

    private List<Faculty> findAllWithSpec(FindAllData data) {
        return facultyRepository.findAll(getSpec(data));
    }

    private List<Faculty> findAllWithSpecAndPagination(FindAllData data) {
        return facultyRepository.findAll(getSpec(data), PageRequest.of(data.getPage(), data.getObjectsPerPage())).getContent();
    }

    private Specification getSpec(FindAllData data) {
        return Specification.where(specification.getObjectByDeletedCriteria(data.getDeleted()));
    }

    @Override
    @Transactional
    public void register(RegisterFacultyData data) {
        checkExistenceByNameBeforeRegistration(new CheckExistsByNameData<>(className, getFacultyName(data), facultyRepository));
        validateObject(new DataForAbstractValidator(facultyValidator, data.getNewFaculty()));

        Faculty newFaculty = data.getNewFaculty();

        saveFaculty(newFaculty);
        saveNewDeaneryWorkers(getDeaneryWorkersFromFaculty(newFaculty), data);
    }

    private String getFacultyName(RegisterFacultyData data) {
        return data.getNewFaculty().getName();
    }

    private void saveFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
    }

    private void saveNewDeaneryWorkers(List<DeaneryWorker> deaneryWorkers, RegisterFacultyData data) {
        for (DeaneryWorker worker : deaneryWorkers)
            deaneryWorkerService.register(new RegisterPersonData<>(worker, data.getBindingResult()));
    }

    private List<DeaneryWorker> getDeaneryWorkersFromFaculty(Faculty faculty) {
        return faculty.getDeaneryWorkers();
    }


    @Override
    @Transactional
    public void update(UpdateFacultyData data) {
        checkExistsWithSuchID(new CheckExistsByIdData(className, data.getId(), facultyRepository));
        checkExistenceByNameBeforeRegistration(new CheckExistsByNameData<>(className, data.getUpdatedFaculty().getName(), facultyRepository));
        validateObject(new DataForAbstractValidator(facultyValidator, data.getUpdatedFaculty()));

        Faculty updatedFaculty = data.getUpdatedFaculty();
        setIdInFaculty(updatedFaculty, data);
        setDepartmentsInFaculty(updatedFaculty, data);
        setDeaneryWorkersInFaculty(updatedFaculty, data);

        saveFaculty(updatedFaculty);
    }

    private void setIdInFaculty(Faculty faculty, UpdateFacultyData data) {
        faculty.setId(getExistingFacultyId(data));
    }

    private Long getExistingFacultyId(UpdateFacultyData data) {
        return getExistingFaculty(getFacultyName(data)).getId();
    }

    private Faculty getExistingFaculty(Long facultyId) {
        return facultyRepository.findById(facultyId).get();
    }

    private Long getFacultyName(UpdateFacultyData data) {
        return data.getId();
    }

    private void setDepartmentsInFaculty(Faculty faculty, UpdateFacultyData data) {
        faculty.setDepartments(getDepartments(data));
    }

    private List<Department> getDepartments(UpdateFacultyData data) {
        return getExistingFaculty(getFacultyName(data)).getDepartments();
    }

    private void setDeaneryWorkersInFaculty(Faculty faculty, UpdateFacultyData data) {
        faculty.setDeaneryWorkers(getDeaneryWorkers(data));
    }

    private List<DeaneryWorker> getDeaneryWorkers(UpdateFacultyData data) {
        return getExistingFaculty(getFacultyName(data)).getDeaneryWorkers();
    }

    @Override
    @Transactional
    public void delete(Long facultyId) {
        checkExistsWithSuchID(new CheckExistsByIdData(className, facultyId, facultyRepository));

        facultyRepository.deleteById(facultyId);
    }

    @Override
    @Transactional
    public void softDelete(Long facultyId) {
        checkExistsWithSuchID(new CheckExistsByIdData(className, facultyId, facultyRepository));

        Faculty faculty = getExistingFaculty(facultyId);

        softDeleteDeaneryWorkers(faculty.getDeaneryWorkers());
        softDeleteDepartments(faculty.getDepartments());

        saveFaculty(markFacultyAsDeleted(faculty));
    }

    private void softDeleteDeaneryWorkers(List<DeaneryWorker> deaneryWorkers) {
        deaneryWorkerService.softDeletePeople(deaneryWorkers);
    }

    private void softDeleteDepartments(List<Department> departments) {
        departmentService.softDeleteDepartments(departments);
    }

    private Faculty markFacultyAsDeleted(Faculty faculty) {
        faculty.setDeleted(true);
        return faculty;
    }
}

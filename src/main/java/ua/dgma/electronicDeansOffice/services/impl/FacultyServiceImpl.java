package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import ua.dgma.electronicDeansOffice.services.interfaces.FacultyService;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.specifications.DeletedSpecification;
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
    private final AbstractValidator facultyValidator;
    private final DeletedSpecification specification;
    private String className;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository,
                              PeopleService<DeaneryWorker> deaneryWorkerService,
                              AbstractValidator facultyValidator,
                              DeletedSpecification specification) {
        this.facultyRepository = facultyRepository;
        this.deaneryWorkerService = deaneryWorkerService;
        this.facultyValidator = facultyValidator;
        this.specification = specification;
        className = Faculty.class.getSimpleName();
    }

    @Override
    public List<Faculty> findByName(String name) {
        return facultyRepository.getByNameContainingIgnoreCase(name).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "name", name)));
    }

    @Override
    public List<Faculty> findAllFaculties(FindAllData data) {
        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return facultyRepository.findAll(specification.getObjectByDeletedCriteria(data.getDeleted()));
        else
            return facultyRepository.findAll(specification.getObjectByDeletedCriteria(data.getDeleted()), PageRequest.of(data.getPage(), data.getObjectsPerPage())).getContent();
    }

    @Override
    @Transactional
    public void registerNew(RegisterFacultyData data) {
        checkExistenceByNameBeforeRegistration(new CheckExistsByNameData<>(className, getFacultyName(data), facultyRepository));
        validateObject(new DataForAbstractValidator(facultyValidator, data.getNewFaculty()));

        Faculty newFaculty = data.getNewFaculty();

        saveFaculty(newFaculty);
        saveNewDeaneryWorkers(getNewDeaneryWorkersFromNewFaculty(newFaculty), data);
    }
    private String getFacultyName(RegisterFacultyData data) {
        return data.getNewFaculty().getName();
    }
    private void saveFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
    }
    private void saveNewDeaneryWorkers(List<DeaneryWorker> deaneryWorkers, RegisterFacultyData data) {
        for (DeaneryWorker worker : deaneryWorkers)
            deaneryWorkerService.registerNew(new RegisterPersonData<>(worker, data.getBindingResult()));
    }
    private List<DeaneryWorker> getNewDeaneryWorkersFromNewFaculty(Faculty faculty) {
        return faculty.getDeaneryWorkers();
    }


    @Override
    @Transactional
    public void updateFaculty(UpdateFacultyData data) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, data.getName(), facultyRepository));
        checkExistenceByNameBeforeRegistration(new CheckExistsByNameData<>(className, data.getUpdatedFaculty().getName(), facultyRepository));
        validateObject(new DataForAbstractValidator(facultyValidator, data.getUpdatedFaculty()));

        Faculty updatedFaculty = data.getUpdatedFaculty();
        setIdInFaculty(updatedFaculty, data);
        setDepartmentsInFaculty(updatedFaculty, data);
        setDeaneryWorkersInFaculty(updatedFaculty, data);

//        Faculty existingFaculty = facultyRepository.getByName(data.getName()).get();
//        Faculty updatedFaculty = data.getUpdatedFaculty();
//
//        setFacultyName(existingFaculty, updatedFaculty);

        saveFaculty(updatedFaculty);
    }

    private void setIdInFaculty(Faculty faculty, UpdateFacultyData data) {
        faculty.setId(getExistingFacultyId(data));
    }
    private Long getExistingFacultyId(UpdateFacultyData data) {
        return getExistingFaculty(getFacultyName(data)).getId();
    }
    private String getFacultyName(UpdateFacultyData data) {
        return data.getName();
    }
    private Faculty getExistingFaculty(String facultyName) {
        return facultyRepository.getByName(facultyName).get();
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
    public void deleteFaculty(String name) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, facultyRepository));

        departmentService.deleteDepartments(getFacultyByName(name));
        facultyRepository.deleteByName(name);
    }
    private Faculty getFacultyByName(String facultyName) {
        return facultyRepository.getByName(facultyName).get();
    }

    @Override
    @Transactional
    public void softDeleteFaculty(String name) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, facultyRepository));

        Faculty faculty = facultyRepository.getByName(name).get();
        faculty.getDepartments().stream().forEach(department -> department.setDeleted(true));
        faculty.getDepartments().stream().forEach(department -> department.getStudentGroups().forEach(studentGroup -> studentGroup.setDeleted(true)));
        faculty.getDepartments().stream().forEach(department -> department.getStudentGroups().forEach(studentGroup -> studentGroup.getStudents().forEach(student -> student.setDeleted(true))));
        faculty.getDepartments().stream().forEach(department -> department.getTeachers().forEach(teacher -> teacher.setDeleted(true)));
        faculty.getDeaneryWorkers().stream().forEach(deaneryWorker -> deaneryWorker.setDeleted(true));
        faculty.setDeleted(true);

        facultyRepository.save(faculty);
    }

}

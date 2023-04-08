package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.faculty.RegisterFacultyData;
import ua.dgma.electronicDeansOffice.services.impl.data.faculty.UpdateFacultyData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.interfaces.FacultyService;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.specifications.DeletedSpecification;
import ua.dgma.electronicDeansOffice.services.specifications.impl.SpecificationsImpl;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByNameData;
import ua.dgma.electronicDeansOffice.utill.validators.AbstractValidator;
import ua.dgma.electronicDeansOffice.utill.validators.data.DataForAbstractValidator;

import java.util.ArrayList;
import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.*;

@Service
@Transactional(readOnly = true)
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final PeopleService<DeaneryWorker> deaneryWorkerService;
    private final AbstractValidator facultyValidator;
    private final ExceptionData exceptionData;
    private final DeletedSpecification specification;
    private String className;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository,
                              PeopleService<DeaneryWorker> deaneryWorkerService,
                              AbstractValidator facultyValidator,
                              ExceptionData exceptionData,
                              DeletedSpecification specification) {
        this.facultyRepository = facultyRepository;
        this.deaneryWorkerService = deaneryWorkerService;
        this.facultyValidator = facultyValidator;
        this.exceptionData = exceptionData;
        this.specification = specification;
        className = Faculty.class.getSimpleName();
    }

    @Override
    public Faculty findByName(String name) {
        return facultyRepository.getByName(name).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "name", name)));
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
        checkExistenceByNameBeforeRegistration(new CheckExistsByNameData<>(className, data.getNewFaculty().getName(), facultyRepository));
        validateObject(new DataForAbstractValidator(facultyValidator, data.getNewFaculty()));

        Faculty newFaculty = data.getNewFaculty();

        for (DeaneryWorker worker: saveFacultyWithoutDeaneryWorkers(newFaculty))
            deaneryWorkerService.registerNew(new RegisterPersonData<>(worker, data.getBindingResult()));
    }

    public List<DeaneryWorker> saveFacultyWithoutDeaneryWorkers(Faculty faculty) {
        List<DeaneryWorker> newDeaneryWorkers = faculty.getDeaneryWorkers();

        faculty.setDeaneryWorkers(new ArrayList<>());
        facultyRepository.save(faculty);

        return newDeaneryWorkers;
    }

    @Override
    @Transactional
    public void updateByName(UpdateFacultyData data) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, data.getName(), facultyRepository));
        validateObject(new DataForAbstractValidator(facultyValidator, data.getUpdatedFaculty()));

        Faculty updatedFaculty = data.getUpdatedFaculty();
        updatedFaculty.setId(facultyRepository.getByName(data.getName()).get().getId());
        updatedFaculty.setDepartments(facultyRepository.getByName(data.getName()).get().getDepartments());
        updatedFaculty.setDeaneryWorkers(facultyRepository.getByName(data.getName()).get().getDeaneryWorkers());

        facultyRepository.save(updatedFaculty);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, facultyRepository));
        facultyRepository.deleteByName(name);
    }

    @Override
    @Transactional
    public void softDeleteByName(String name) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, facultyRepository));

        Faculty faculty = findByName(name);
        faculty.getDepartments().stream().forEach(department -> department.setDeleted(true));
        faculty.getDepartments().stream().forEach(department -> department.getStudentGroups().forEach(studentGroup -> studentGroup.setDeleted(true)));
        faculty.getDepartments().stream().forEach(department -> department.getStudentGroups().forEach(studentGroup -> studentGroup.getStudents().forEach(student -> student.setDeleted(true))));
        faculty.getDepartments().stream().forEach(department -> department.getTeachers().forEach(teacher -> teacher.setDeleted(true)));
        faculty.getDeaneryWorkers().stream().forEach(deaneryWorker -> deaneryWorker.setDeleted(true));
        faculty.setDeleted(true);

        facultyRepository.save(faculty);
    }

}

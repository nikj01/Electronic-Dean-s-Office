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
import ua.dgma.electronicDeansOffice.services.interfaces.FacultyService;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.specifications.Specifications;
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
    private final DepartmentRepository departmentRepository;
    private final PeopleService<DeaneryWorker> deaneryWorkerService;
    private final AbstractValidator facultyValidator;
    private final ExceptionData exceptionData;
    private final Specifications<Faculty> specifications;
    private String className;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository,
                              DepartmentRepository departmentRepository, PeopleService<DeaneryWorker> deaneryWorkerService,
                              AbstractValidator facultyValidator,
                              ExceptionData exceptionData,
                              Specifications<Faculty> specifications) {
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
        this.deaneryWorkerService = deaneryWorkerService;
        this.facultyValidator = facultyValidator;
        this.exceptionData = exceptionData;
        this.specifications = specifications;
        className = Faculty.class.getSimpleName();
    }

    @Override
    public Faculty findById(Long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "id", id)));
    }

    @Override
    public Faculty findByName(String name) {
        return facultyRepository.getByName(name).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "name", name)));
    }

    @Override
    public List<Faculty> findAllWithPaginationOrWithout(Integer page, Integer facultiesPerPage, Boolean isDeleted) {
        if(checkPaginationParameters(page, facultiesPerPage))
            return facultyRepository.findAll(specifications.getObjectByDeletedCriteria(isDeleted));
        else
            return facultyRepository.findAll(specifications.getObjectByDeletedCriteria(isDeleted), PageRequest.of(page, facultiesPerPage)).getContent();
    }

    @Override
    @Transactional
    public void registerNew(Faculty faculty, BindingResult bindingResult) {
        checkExistenceByNameBeforeRegistration(new CheckExistsByNameData<>(Faculty.class.getSimpleName(), faculty.getName(), facultyRepository));
        validateObject(new DataForAbstractValidator(facultyValidator, faculty));

        for (DeaneryWorker worker: saveFacultyWithoutDeaneryWorkers(faculty))
            deaneryWorkerService.registerNew(worker, bindingResult);
    }

    public List<DeaneryWorker> saveFacultyWithoutDeaneryWorkers(Faculty faculty) {
        List<DeaneryWorker> newDeaneryWorkers = faculty.getDeaneryWorkers();

        faculty.setDeaneryWorkers(new ArrayList<>());
        facultyRepository.save(faculty);

        return newDeaneryWorkers;
    }

    @Override
    @Transactional
    public void updateByName(String name, Faculty updatedFaculty, BindingResult bindingResult) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, facultyRepository));
        validateObject(new DataForAbstractValidator(facultyValidator, updatedFaculty));

        updatedFaculty.setId(facultyRepository.getByName(name).get().getId());
        updatedFaculty.setDepartments(facultyRepository.getByName(name).get().getDepartments());
        updatedFaculty.setDeaneryWorkers(facultyRepository.getByName(name).get().getDeaneryWorkers());

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

package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.people.ExceptionData;
import ua.dgma.electronicDeansOffice.exceptions.people.NotFoundException;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.FacultyService;
import ua.dgma.electronicDeansOffice.utill.validators.FacultyValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ErrorsBuilder.returnErrorsToClient;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkPaginationParameters;

public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyValidator facultyValidator;
    private final ExceptionData exceptionData;
    private String className;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository,
                              FacultyValidator facultyValidator,
                              ExceptionData exceptionData) {
        this.facultyRepository = facultyRepository;
        this.facultyValidator = facultyValidator;
        this.exceptionData = exceptionData;
        className = Faculty.class.getSimpleName();
    }


    @Override
    public Faculty findByName(String name) {
        return facultyRepository.getByName(name).orElseThrow(() -> new NotFoundException(new ExceptionData<String>(className, "name", name)));
    }

    @Override
    public List<Faculty> findAllWithPaginationOrWithout(Integer page, Integer peoplePerPage) {
        if(checkPaginationParameters(page, peoplePerPage))
            return facultyRepository.findAll();
        else
            return facultyRepository.findAll(PageRequest.of(page, peoplePerPage)).getContent();
    }

    @Override
    public void registerNew(Faculty faculty, BindingResult bindingResult) {
        validateFaculty(faculty, bindingResult);
        facultyRepository.save(faculty);
    }

    @Override
    public void updateByName(String name, Faculty updatedFaculty, BindingResult bindingResult) {
        checkExistsWithSuchName(name);
        validateFaculty(updatedFaculty, bindingResult);

        updatedFaculty.setName(name);

        facultyRepository.save(updatedFaculty);
    }

    @Override
    public void deleteByName(String name) {
        checkExistsWithSuchName(name);
        facultyRepository.deleteByName(name);
    }

    @Override
    public void validateFaculty(Faculty faculty, BindingResult bindingResult) {
        facultyValidator.validate(faculty, bindingResult);
        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
    }

    @Override
    public void checkExistsWithSuchName(String name) {
        if(!facultyRepository.existsByName(name)) throw new NotFoundException(new ExceptionData<String>(className, "name", name));
    }
}

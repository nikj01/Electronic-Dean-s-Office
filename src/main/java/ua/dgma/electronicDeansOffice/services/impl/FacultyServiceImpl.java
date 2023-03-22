package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.ExceptionData;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.FacultyService;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.FacultyValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkPaginationParameters;

import ua.dgma.electronicDeansOffice.utill.ValidationData;

@Service
@Transactional(readOnly = true)
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
        return facultyRepository.findById(name).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "name", name)));
    }

    @Override
    public List<Faculty> findAllWithPaginationOrWithout(Integer page, Integer peoplePerPage) {
        if(checkPaginationParameters(page, peoplePerPage))
            return facultyRepository.findAll();
        else
            return facultyRepository.findAll(PageRequest.of(page, peoplePerPage)).getContent();
    }

    @Override
    @Transactional
    public void registerNew(Faculty faculty, BindingResult bindingResult) {
        validateObject(new ValidationData<>(facultyValidator, faculty, bindingResult));
        facultyRepository.save(faculty);
    }

    @Override
    @Transactional
    public void updateByName(String name, Faculty updatedFaculty, BindingResult bindingResult) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, name, facultyRepository));
        validateObject(new ValidationData<>(facultyValidator, updatedFaculty, bindingResult));

        updatedFaculty.setName(name);

        facultyRepository.save(updatedFaculty);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, name, facultyRepository));
        facultyRepository.deleteByName(name);
    }

}

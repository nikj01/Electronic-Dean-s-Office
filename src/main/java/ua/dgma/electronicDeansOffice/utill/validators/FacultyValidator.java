package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.repositories.DeaneryWorkerRepository;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;

import java.util.List;

@Component
public class FacultyValidator implements Validator {

    private final FacultyRepository facultyRepository;
    private final DeaneryWorkerValidator deaneryWorkerValidator;

    @Autowired
    public FacultyValidator(FacultyRepository facultyRepository,
                            DeaneryWorkerRepository deaneryWorkerRepository,
                            DeaneryWorkerValidator deaneryWorkerValidator) {
        this.facultyRepository = facultyRepository;
        this.deaneryWorkerValidator = deaneryWorkerValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Faculty.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Faculty faculty = (Faculty) target;

        if(facultyRepository.getByName(faculty.getName()).isPresent())
            errors.rejectValue("name", "Faculty with NAME " + faculty.getName() + " already exists!" );
        if(!faculty.getDeaneryWorkers().isEmpty()) {
            for (DeaneryWorker worker : faculty.getDeaneryWorkers())
                deaneryWorkerValidator.validate(worker, errors);
        }
    }
}

package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.exceptions.IncorrectPropertyException;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.utill.validators.data.FacultyValidationData;

import java.util.List;

@Component
public class FacultyValidator implements AbstractValidator {

    private final FacultyRepository facultyRepository;
    private final PeopleRepository<Person> deaneryWorkerRepository;

    @Autowired
    public FacultyValidator(FacultyRepository facultyRepository,
                            PeopleRepository<Person> deaneryWorkerRepository) {
        this.facultyRepository = facultyRepository;
        this.deaneryWorkerRepository = deaneryWorkerRepository;
    }

    @Override
    public void validate(Object target) {
        Faculty faculty = (Faculty) target;
        FacultyValidationData validationData = new FacultyValidationData(faculty, facultyRepository, deaneryWorkerRepository);

        if(checkExistenceOfTheFaculty(validationData)) {

        } else {
            checkExistenceOfTheDeaneryWorkers(validationData);
        }
    }

    private boolean checkExistenceOfTheFaculty(FacultyValidationData data) {
        if(data.getFacultyRepository().getByName(data.getFaculty().getName()).isPresent()) return true; else return false;
    }

    private void checkExistenceOfTheDeaneryWorkers(FacultyValidationData data) {
        List<DeaneryWorker> newDeaneryWorkers = data.getFaculty().getDeaneryWorkers();

        for (DeaneryWorker worker : newDeaneryWorkers) {
            checkDeaneryWorkerByUid(worker, data);
        }
    }

    private void checkDeaneryWorkerByUid(DeaneryWorker deaneryWorker, FacultyValidationData data) {
        if(data.getDeaneryWorkerRepository().getByUid(deaneryWorker.getUid()).isPresent())
            throw new IncorrectPropertyException("Person with uid " + deaneryWorker.getUid() + " already exists!");
    }
}

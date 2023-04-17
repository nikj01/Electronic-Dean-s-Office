package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.exceptions.IncorrectPropertyException;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Component
public class FacultyValidator implements AbstractValidator {
    private final PeopleRepository<Person> deaneryWorkerRepository;

    @Autowired
    public FacultyValidator(PeopleRepository<Person> deaneryWorkerRepository) {
        this.deaneryWorkerRepository = deaneryWorkerRepository;
    }

    @Override
    public void validate(Object target) {
        Faculty faculty = (Faculty) target;

        if (!checkExistenceOfTheFaculty(faculty))
            checkExistenceOfTheDeaneryWorkers(faculty);
    }

    private boolean checkExistenceOfTheFaculty(Faculty faculty) {
        if (faculty.getId() != null) return true;
        else return false;
    }

    private void checkExistenceOfTheDeaneryWorkers(Faculty faculty) {
        if (getDeaneryWorkers(faculty) != null)
            for (DeaneryWorker worker : getDeaneryWorkers(faculty))
                checkDeaneryWorkerByUid(worker);
    }

    private List<DeaneryWorker> getDeaneryWorkers(Faculty faculty) {
        return faculty.getDeaneryWorkers();
    }

    private void checkDeaneryWorkerByUid(DeaneryWorker deaneryWorker) {
        if (checkWorkerById(deaneryWorker).isPresent())
            throw new IncorrectPropertyException("Person with uid " + getWorkerId(deaneryWorker) + " already exists!");
    }

    private Optional<Person> checkWorkerById(DeaneryWorker deaneryWorker) {
        return deaneryWorkerRepository.findById(getWorkerId(deaneryWorker));
    }

    private Long getWorkerId(DeaneryWorker deaneryWorker) {
        return deaneryWorker.getUid();
    }
}

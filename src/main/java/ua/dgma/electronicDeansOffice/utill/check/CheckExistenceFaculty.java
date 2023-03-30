package ua.dgma.electronicDeansOffice.utill.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;

@Component
public class CheckExistenceFaculty {

    private static FacultyRepository repository;

    @Autowired
    public CheckExistenceFaculty(FacultyRepository repository) {
        this.repository = repository;
    }

    public static void checkExistenceFaculty(Faculty faculty, Errors errors) {
        if(!repository.getByName(faculty.getName()).isPresent())
            errors.rejectValue("name", "Faculty with NAME " + faculty.getName() + " does not exist!");
    }
}

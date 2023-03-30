package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;

@Component
public class FacultyValidator implements Validator {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyValidator(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
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
    }
}

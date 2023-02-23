package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;

@Component
public class StudentGroupValidator implements Validator {

    private final StudentGroupRepository repository;

    @Autowired
    public StudentGroupValidator(StudentGroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return StudentGroup.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentGroup group = (StudentGroup) target;

        if(!repository.findById(group.getName()).isPresent())
            errors.rejectValue("name", "Student group with such NAME does not exist!");
    }
}

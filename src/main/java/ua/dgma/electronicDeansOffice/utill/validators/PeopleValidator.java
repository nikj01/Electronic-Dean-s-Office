package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;

@Component
public class PeopleValidator implements Validator {

    private final PeopleRepository repository;

    @Autowired
    public PeopleValidator(PeopleRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(repository.getByUid(person.getUid()).isPresent())
            errors.rejectValue("uid", "Person with such UID already exists!");
        if(repository.getByEmail(person.getEmail()).isPresent())
            errors.rejectValue("email", "Person with such EMAIL already exists!");
    }
}

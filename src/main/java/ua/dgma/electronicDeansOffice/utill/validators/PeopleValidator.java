package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;

@Component
public class PeopleValidator implements Validator {

    private final PeopleRepository repository;

    @Autowired
    public PeopleValidator(@Qualifier("peopleRepository") PeopleRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        checkExistenceOfThePersonById(person, errors);
//        if(repository.getByUid(person.getUid()).isPresent())
//            errors.rejectValue("uid", "Person with UID " + person.getUid() + " already exists!");
    }

    private void checkExistenceOfThePersonById(Person person, Errors errors) {
        if(repository.getByUid(person.getUid()).isPresent())
            errors.rejectValue("uid", "Person with UID " + person.getUid() + " already exists!");
    }
}

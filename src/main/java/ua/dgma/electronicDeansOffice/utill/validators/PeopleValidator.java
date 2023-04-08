package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.utill.validators.data.PersonValidationData;

@Component
public class PeopleValidator implements Validator {

    private final PeopleRepository<Person> repository;

    @Autowired
    public PeopleValidator(@Qualifier("peopleRepository") PeopleRepository<Person> repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        PersonValidationData validationData = new PersonValidationData(person, repository, errors);

        if(checkPersonsId(validationData)) {

        } else {
            checkExistenceOfThePersonById(validationData);
        }
    }

    private boolean checkPersonsId(PersonValidationData data) {
        if(data.getPerson().getUid() == null) return true; else return false;
    }

    private void checkExistenceOfThePersonById(PersonValidationData data) {
        if(data.getPeopleRepository().getByUid(data.getPerson().getUid().longValue()).isPresent())
            data.getErrors().rejectValue("uid", "Person with UID " + data.getPerson().getUid() + " already exists!");
    }

}

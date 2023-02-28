package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.people.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.utill.validators.PeopleValidator;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl extends PeopleServiceImpl<Person> {

    private final PeopleRepository<Person> personRepository;

    @Autowired
    protected PersonServiceImpl(PeopleRepository<Person> personRepository,
                                PeopleValidator validator,
                                ExceptionData exceptionData) {
        super(personRepository, validator, exceptionData);
        this.personRepository = personRepository;
    }

    @Override
    public void updateByUid(Long uid, Person updatedPerson, BindingResult bindingResult) {
        checkExistsWithSuchUid(uid);
        validatePerson(updatedPerson, bindingResult);

        updatedPerson.setUid(uid);
        personRepository.save(updatedPerson);
    }

}

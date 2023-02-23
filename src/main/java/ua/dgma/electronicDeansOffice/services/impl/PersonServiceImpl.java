package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.utill.validators.PeopleValidator;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl extends PeopleServiceImpl<Person>{

    private final PeopleRepository<Person> personRepository;
    private final PeopleValidator personValidator;

    @Autowired
    protected PersonServiceImpl(PeopleRepository<Person> repository,
                                PeopleRepository<Person> personRepository,
                                PeopleValidator personValidator) {
        super(repository, personValidator);
        this.personRepository = personRepository;
        this.personValidator = personValidator;
    }

    @Override
    public void updateByUid(Long uid, Person updatedPerson, BindingResult bindingResult) {
        checkExistsWithSuchUid(uid);
        validate(updatedPerson, bindingResult);

        updatedPerson.setUid(uid);
        personRepository.save(updatedPerson);
    }

//    @Override
//    public void validate(Person person, BindingResult bindingResult) {
//        personValidator.validate(person, bindingResult);
//        if(bindingResult.hasErrors())
//            returnErrorsToClient(bindingResult);
//    }

}

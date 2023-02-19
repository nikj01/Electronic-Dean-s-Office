package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.PersonExceptions.PersonNotFoundException;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.utill.validators.PersonValidator;

import java.util.List;
import java.util.Optional;

import static ua.dgma.electronicDeansOffice.utill.ErrorsBuilder.returnErrorsToClient;


@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PeopleService<Person> {

    private final PeopleRepository<Person> repository;

    private final PersonValidator validator;

    @Autowired
    public PersonServiceImpl(PeopleRepository<Person> repository, PersonValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }


    @Override
    public Person findByUid(Long uid) {
        Optional<Person> foundPerson = repository.getByUid(uid);
        return foundPerson.orElseThrow(() -> new <String, Long> PersonNotFoundException("uid", uid ));
    }

    @Override
    public Person findByEmail(String email) {
        Optional<Person> foundPerson = repository.getByEmail(email);
        return foundPerson.orElseThrow(() -> new <String, String> PersonNotFoundException("email", email));
    }

    @Override
    public List<Person> findBySurname(String surname) {
        if(!repository.existsBySurname(surname)) throw new <String, String> PersonNotFoundException("surname", surname);

        return repository.getBySurname(surname);
    }

    @Override
    public List<Person> findAll(Integer page, Integer peoplePerPage) {
        if(page == null || peoplePerPage == null)
            return repository.findAll();
        else
            return repository.findAll(PageRequest.of(page, peoplePerPage)).getContent();
    }

    @Override
    @Transactional
    public void registerNew(Person t, BindingResult bindingResult) {
        validate(t, bindingResult);

        repository.save(t);
    }

    @Transactional
    public void updateByUid(Long uid, Person t, BindingResult bindingResult) {
        if(!repository.existsByUid(uid)) throw new <String, Long> PersonNotFoundException("uid", uid );

        validate(t, bindingResult);

        t.setUid(uid);

        repository.save(t);
    }

    @Override
    @Transactional
    public void deleteByUId(Long uid) {
        if(!repository.existsByUid(uid)) throw new <String, Long> PersonNotFoundException("uid", uid );

        repository.deleteByUid(uid);
    }

    @Override
    public void validate(Person person, BindingResult bindingResult) {
        validator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
    }

}

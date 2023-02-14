package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PersonRepository;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PersonServiceImpl {

    private final PersonRepository repository;

    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }


    public Person findOne(Long uid){
        Optional<Person> foundPerson = repository.findByUid(uid);
        return foundPerson.orElse(null);
    }
}

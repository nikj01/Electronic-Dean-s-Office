package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.PersonExceptions.PersonWithThisUidDoesntExist;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonsGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.impl.PersonMapperImpl;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PeopleService<Person, PersonGetDTO, PersonsGetDTO> {

    private final PeopleRepository<Person> repository;

    @Autowired
    public PersonServiceImpl(PeopleRepository<Person> repository, PersonMapperImpl mapper) {
        this.repository = repository;
    }


    @Override
    public Person findOnePersonByUid(Long uid) {
        Optional<Person> foundPerson = repository.getByUid(uid);
        return foundPerson.orElse(null);
    }

    @Override
    public Person findOnePersonByEmail(String email) {
        Optional<Person> foundPerson = repository.getByEmail(email);
        return foundPerson.orElse(null);
    }

    @Override
    public Person findOnePersonBySurname(String surname) {
        Optional<Person> foundPerson = repository.getBySurname(surname);
        return foundPerson.orElse(null);
    }

    @Override
    public List<Person> findAllPeople() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void registerNewPerson(Person newPerson) {
        repository.save(newPerson);
    }

    @Override
    @Transactional
    public void updatePersonByUid(Long uid, Person updatedPerson) {
        updatedPerson.setUid(uid);
        repository.save(updatedPerson);
    }

    @Override
    @Transactional
    public void deletePerson(Person personToDelete) {

    }

}

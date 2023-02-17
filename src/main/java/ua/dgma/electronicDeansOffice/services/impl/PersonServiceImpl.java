package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PeopleGetDTO;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PeopleService<Person, PersonGetDTO, PeopleGetDTO> {

    private final PeopleRepository<Person> repository;

    @Autowired
    public PersonServiceImpl(PeopleRepository<Person> repository) {
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
    @Modifying
    public void updatePersonByUidPut(Long uid, Person updatedPerson) {
        //updatedPerson.setUid(uid);

        Person personToBeUpdate = repository.getByUid(uid).get();
        personToBeUpdate.setUid(updatedPerson.getUid());
        personToBeUpdate.setSurname(updatedPerson.getSurname());
        personToBeUpdate.setName(updatedPerson.getName());
        personToBeUpdate.setPatronymic(updatedPerson.getPatronymic());
        personToBeUpdate.setDateOfBirth(updatedPerson.getDateOfBirth());
        personToBeUpdate.setEmail(updatedPerson.getEmail());
        personToBeUpdate.setRole(updatedPerson.getRole());
        personToBeUpdate.setPassword(updatedPerson.getPassword());

        repository.save(personToBeUpdate);

//        Person personToBeUpdate = repository.getByUid(uid).get();
//        personToBeUpdate = updatedPerson;
//        repository.saveAndFlush(personToBeUpdate);
    }

    @Transactional
    public void updatePersonByUidPatch(Long uid, Person updatedPerson) {
        updatedPerson.setUid(uid);

        repository.save(updatedPerson);
    }

}

package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PeopleService<Person> {

    private final PeopleRepository<Person> repository;

    @Autowired
    public PersonServiceImpl(PeopleRepository repository) {
        this.repository = repository;
    }


//    public Person findOne(Long uid){
//        Optional<Person> foundPerson = repository.getPersonByUid(uid);
//        return foundPerson.orElse(null);
//    }

    @Override
    public Person findOneByUid(Long uid) {
        Optional<Person> foundPerson = repository.getByUid(uid);
        return foundPerson.orElse(null);
    }

    public List<Person> findAll(){
        return repository.findAll();
    }
}

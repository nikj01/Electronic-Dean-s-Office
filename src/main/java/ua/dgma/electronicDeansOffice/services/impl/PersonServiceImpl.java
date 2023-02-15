package ua.dgma.electronicDeansOffice.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonsResponseDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.PersonMapper;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PeopleService<Person, PersonDTO>, PersonMapper {

    private final PeopleRepository<Person> repository;
    private final ModelMapper mapper;

    @Autowired
    public PersonServiceImpl(PeopleRepository<Person> repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public PersonDTO findOneByUid(Long uid) {
        Optional<Person> foundPerson = repository.getByUid(uid);
        return convertToPersonDTO(foundPerson.orElse(null));
    }

    @Override
    public PersonDTO findOneByEmail(String email) {
        Optional<Person> foundPerson = repository.getByEmail(email);
        return convertToPersonDTO(foundPerson.orElse(null));
    }

    @Override
    public PersonDTO findOneBySurname(String surname) {
        Optional<Person> foundPerson = repository.getBySurname(surname);
        return convertToPersonDTO(foundPerson.orElse(null));
    }

    @Override
    public List<PersonDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::convertToPersonDTO)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void registerNewPerson(PersonDTO person) {
        Person newPerson = convertToPerson(person);
        repository.save(newPerson);
    }

    @Override
    public PersonDTO convertToPersonDTO(Person person) {
        return mapper.map(person, PersonDTO.class);
    }

    @Override
    public Person convertToPerson(PersonDTO personDTO) {
        return mapper.map(personDTO, Person.class);
    }







//    public Person findOne(Long uid){
//        Optional<Person> foundPerson = repository.getPersonByUid(uid);
//        return foundPerson.orElse(null);
//    }

//    @Override
//    public Person findOneByUid(Long uid) {
//        Optional<Person> foundPerson = repository.getByUid(uid);
//        return foundPerson.orElse(null);
//    }
//
//    @Override
//    public Student findOneByUid(Long uid) {
//        Optional<Student> foundStudent = repository.getByUid(uid);
//        return foundStudent.orElse(null);
//    }
//
//    @Override
//    public Person findOneByEmail(String email) {
//        return null;
//    }
//
//    public List<Person> findAll(){
//        return repository.findAll();
//    }
//
//    public Person findOneBySurname(String surname){
//        Optional<Person> foundPerson = repository.getBySurname(surname);
//        return foundPerson.orElse(null);
//    }
//
//    public void savePerson(Person person){
//        repository.save(person);
//    }
}

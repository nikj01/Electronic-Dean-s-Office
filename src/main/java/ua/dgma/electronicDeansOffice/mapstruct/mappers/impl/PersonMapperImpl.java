package ua.dgma.electronicDeansOffice.mapstruct.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonsGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.PersonMapper;
import ua.dgma.electronicDeansOffice.models.Person;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonMapperImpl implements PersonMapper {

    private final ModelMapper mapper;

    @Autowired
    public PersonMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PersonGetDTO convertToPersonDTO(Person person) {
        return mapper.map(person, PersonGetDTO.class);
    }

    @Override
    public Person convertToPerson(PersonGetDTO personGetDTO) {
        return mapper.map(personGetDTO, Person.class);
    }

    @Override
    public PersonsGetDTO convertPersonsToPersonsDTO(List<Person> persons) {
        return new PersonsGetDTO(persons.stream()
                      .map(this::convertToPersonDTO)
                      .sorted()
                      .collect(Collectors.toList())
        );
    }


}

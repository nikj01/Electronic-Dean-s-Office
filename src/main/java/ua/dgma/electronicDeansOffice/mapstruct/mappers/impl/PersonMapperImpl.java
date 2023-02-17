package ua.dgma.electronicDeansOffice.mapstruct.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.*;
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
    public Person convertToPerson(PersonPostDTO personPost) {
        return mapper.map(personPost, Person.class);
    }

    @Override
    public PersonGetDTO convertToPersonGetDTO(Person person) {
        return mapper.map(person, PersonGetDTO.class);
    }

    @Override
    public PersonSlimGetDTO convertToPersonSlimGetDTO(Person person) {
        return mapper.map(person, PersonSlimGetDTO.class);
    }

    @Override
    public PeopleGetDTO convertToPeopleDTO(List<Person> people) {
        return new PeopleGetDTO(people.stream()
                                      .map(this::convertToPersonGetDTO)
                                      .sorted()
                                      .collect(Collectors.toList())
        );
    }

    @Override
    public PeopleSlimGetDTO convertToPeopleSlimDTO(List<Person> people) {
        return new PeopleSlimGetDTO(people.stream()
                                          .map(this::convertToPersonSlimGetDTO)
                                          .sorted()
                                          .collect(Collectors.toList()));
    }
}

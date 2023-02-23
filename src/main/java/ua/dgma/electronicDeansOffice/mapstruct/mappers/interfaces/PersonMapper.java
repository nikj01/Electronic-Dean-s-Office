package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.*;
import ua.dgma.electronicDeansOffice.models.Person;

import java.util.List;

public interface PersonMapper {

    Person convertToPerson(PersonPostDTO personPost);

    PersonGetDTO convertToPersonGetDTO(Person person);

    PersonSlimGetDTO convertToPersonSlimGetDTO(Person person);

    PeopleGetDTO convertToPeopleGetDTO(List<Person> people);

    PeopleSlimGetDTO convertToPeopleSlimGetDTO(List<Person> people);
}

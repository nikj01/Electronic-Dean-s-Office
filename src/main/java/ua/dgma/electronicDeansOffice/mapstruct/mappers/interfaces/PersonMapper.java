package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;


import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonsGetDTO;
import ua.dgma.electronicDeansOffice.models.Person;

import java.util.List;

//@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonGetDTO convertToPersonDTO(Person person);

    Person convertToPerson(PersonGetDTO personGetDTO);

    PersonsGetDTO convertPersonsToPersonsDTO(List<Person> persons);

}

package ua.dgma.electronicDeansOffice.mapstruct.mappers;


import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonDTO;
import ua.dgma.electronicDeansOffice.models.Person;


public interface PersonMapper {

    PersonDTO convertToPersonDTO(Person person);

    Person convertToPerson(PersonDTO personDTO);

}

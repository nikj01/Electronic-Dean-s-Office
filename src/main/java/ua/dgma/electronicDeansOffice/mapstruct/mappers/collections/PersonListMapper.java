package ua.dgma.electronicDeansOffice.mapstruct.mappers.collections;

import org.mapstruct.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.PersonMapper;
import ua.dgma.electronicDeansOffice.models.Person;

import java.util.List;

@Mapper(componentModel = "spring", uses = PersonMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PersonListMapper {

    @IterableMapping(qualifiedByName = "person")
    @Named(value = "peopleGet")
    List<PersonGetDTO> toPeopleGetDTO(List<Person> people);
    @IterableMapping(qualifiedByName = "slimPerson")
    @Named(value = "peopleSlimGet")
    List<PersonSlimGetDTO> toPeopleSlimGetDTO(List<Person> people);

}

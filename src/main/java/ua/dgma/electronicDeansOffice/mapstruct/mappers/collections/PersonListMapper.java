package ua.dgma.electronicDeansOffice.mapstruct.mappers.collections;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.TargetType;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.PersonMapper;
import ua.dgma.electronicDeansOffice.models.Person;

import java.util.List;

@Mapper(componentModel = "spring", uses = PersonMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PersonListMapper {

    @IterableMapping(qualifiedByName = "person")
    List<PersonGetDTO> toPeopleGetDTO(List<Person> people);
    @IterableMapping(qualifiedByName = "slimPerson")
    List<PersonSlimGetDTO> toPeopleSlimGetDTO(List<Person> people);

}

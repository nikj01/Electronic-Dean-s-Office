package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.Person;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PersonMapper {
    @Named("postPerson")
    Person toPerson(PersonPostDTO personPostDTO);
    @Named("patchPerson")
    Person toPerson(PersonPatchDTO personPatchDTO);
    @Named("person")
    PersonGetDTO toPersonGetDTO(Person person);
    @Named("slimPerson")
    PersonSlimGetDTO toPersonSlimGetDTO(Person person);
}

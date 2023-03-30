package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.*;
import ua.dgma.electronicDeansOffice.models.Person;

import java.util.List;
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PersonMapper {

    Person toPerson(PersonPostDTO personPost);
    @Named(value = "person")
    PersonGetDTO toPersonGetDTO(Person person);
    @Named(value = "slimPerson")
    PersonSlimGetDTO toPersonSlimGetDTO(Person person);
}

package ua.dgma.electronicDeansOffice.mapstruct.mappers.collections;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.JournalPageMapper;

@Mapper(componentModel = "spring", uses = {JournalPageMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface JournalPageListMapper {
}

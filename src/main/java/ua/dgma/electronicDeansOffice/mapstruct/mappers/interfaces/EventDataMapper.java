package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.eventData.EventDataGetDTO;
import ua.dgma.electronicDeansOffice.models.EventData;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventDataMapper {
    EventDataGetDTO toEventDataGetDTO(EventData eventData);
}

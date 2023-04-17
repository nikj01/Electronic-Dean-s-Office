package ua.dgma.electronicDeansOffice.mapstruct.mappers.collections;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.event.EventSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.EventMapper;
import ua.dgma.electronicDeansOffice.models.Event;

import java.util.List;

@Mapper(componentModel = "spring", uses = EventMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventListMapper {
    @IterableMapping(qualifiedByName = "slimEvent")
    List<EventSlimGetDTO> toEventsSlimGetDTO(List<Event> events);
}

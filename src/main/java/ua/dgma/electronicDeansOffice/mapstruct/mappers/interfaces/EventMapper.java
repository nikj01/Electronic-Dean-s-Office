package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.event.EventGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.event.EventPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.event.EventPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.event.EventSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.Event;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventMapper {
    @Named("postEvent")
    Event toEvent(EventPostDTO eventPostDTO);
    @Named("patchEvent")
    Event toEvent(EventPatchDTO eventPatchDTO);
    @Named("event")
    EventGetDTO toEventGetDTO(Event event);
    @Named("slimEvent")
    EventSlimGetDTO toEventSlimGetDTO(Event event);
}

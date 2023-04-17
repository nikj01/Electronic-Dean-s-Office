package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.event.EventGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.event.EventPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.event.EventPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.EventMapper;
import ua.dgma.electronicDeansOffice.models.Event;
import ua.dgma.electronicDeansOffice.services.impl.data.event.RegisterEventData;
import ua.dgma.electronicDeansOffice.services.impl.data.event.UpdateEventData;
import ua.dgma.electronicDeansOffice.services.interfaces.EventService;

import javax.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventsController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @Autowired
    public EventsController(EventService eventService,
                            EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @GetMapping("/findById")
    @ResponseStatus(HttpStatus.FOUND)
    public EventGetDTO findEventById(@RequestParam("id") Long eventId) {
        return eventMapper.toEventGetDTO(eventService.findOne(eventId));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewEvent(@RequestBody @Valid EventPostDTO eventPostDTO,
                                 BindingResult bindingResult) {
        Event newEvent = eventMapper.toEvent(eventPostDTO);

        eventService.register(new RegisterEventData(newEvent, bindingResult));
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateEvent(@RequestParam("id") Long eventId,
                            @RequestBody @Valid EventPatchDTO eventPatchDTO,
                            BindingResult bindingResult) {
        Event updatedEvent = eventMapper.toEvent(eventPatchDTO);

        eventService.update(new UpdateEventData(eventId, updatedEvent, bindingResult));
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEvent(@RequestParam("id") Long eventId) {
        eventService.delete(eventId);
    }
}
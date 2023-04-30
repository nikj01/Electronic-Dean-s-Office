package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.event.EventGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.event.EventPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.event.EventPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.EventMapper;
import ua.dgma.electronicDeansOffice.models.Event;
import ua.dgma.electronicDeansOffice.models.EventTypeEnum;
import ua.dgma.electronicDeansOffice.models.PersonRoleEnum;
import ua.dgma.electronicDeansOffice.security.annotations.AllPerople;
import ua.dgma.electronicDeansOffice.security.annotations.IsAdmin;
import ua.dgma.electronicDeansOffice.security.annotations.IsRoot;
import ua.dgma.electronicDeansOffice.security.annotations.IsTeacher;
import ua.dgma.electronicDeansOffice.services.impl.data.event.RegisterEventData;
import ua.dgma.electronicDeansOffice.services.impl.data.event.UpdateEventData;
import ua.dgma.electronicDeansOffice.services.interfaces.EventService;
import ua.dgma.electronicDeansOffice.services.interfaces.ReportService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventsController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final ReportService reportService;

    @Autowired
    public EventsController(EventService eventService,
                            EventMapper eventMapper, ReportService reportService) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.reportService = reportService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @AllPerople
    public EventGetDTO findEventById(@PathVariable("id") Long eventId) {
        return eventMapper.toEventGetDTO(eventService.findOne(eventId));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @IsRoot
    @IsAdmin
    @IsTeacher
    public void registerNewEvent(@RequestBody @Valid EventPostDTO eventPostDTO, BindingResult bindingResult) {
        Event newEvent = eventMapper.toEvent(eventPostDTO);

        eventService.register(new RegisterEventData(newEvent, bindingResult));
    }

    @PatchMapping("{id}/update")
    @ResponseStatus(HttpStatus.OK)
    @IsRoot
    @IsAdmin
    @IsTeacher
    public void updateEvent(@PathVariable("id") Long eventId,
                            @RequestBody @Valid EventPatchDTO eventPatchDTO,
                            BindingResult bindingResult) {
        Event updatedEvent = eventMapper.toEvent(eventPatchDTO);

        eventService.update(new UpdateEventData(eventId, updatedEvent, bindingResult));
    }

    @DeleteMapping("{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    @IsRoot
    @IsAdmin
    @IsTeacher
    public void deleteEvent(@PathVariable("id") Long eventId) {
        eventService.delete(eventId);
    }

    @GetMapping("/types")
    @AllPerople
    public List<String> getAllEventTypes() {
        return Arrays.stream(EventTypeEnum.values()).map(type -> type.name()).collect(Collectors.toList());
    }
}

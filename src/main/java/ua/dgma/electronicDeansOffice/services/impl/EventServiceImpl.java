package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Event;
import ua.dgma.electronicDeansOffice.models.EventTypeEnum;
import ua.dgma.electronicDeansOffice.models.JournalPage;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.EventRepository;
import ua.dgma.electronicDeansOffice.repositories.JournalPageRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.event.RegisterEventData;
import ua.dgma.electronicDeansOffice.services.impl.data.event.UpdateEventData;
import ua.dgma.electronicDeansOffice.services.interfaces.EventService;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.EventValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceObjectWithSuchID;

@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final StudentGroupService groupService;
    private final JournalPageRepository pageRepository;
    private final EventValidator eventValidator;
    private String className;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            StudentGroupService groupService,
                            JournalPageRepository pageRepository,
                            EventValidator eventValidator) {
        this.eventRepository = eventRepository;
        this.groupService = groupService;
        this.pageRepository = pageRepository;
        this.eventValidator = eventValidator;
        this.className = Event.class.getSimpleName();
    }

    @Override
    public Event findOne(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "id", eventId)));
    }

    @Override
    @Transactional
    public void register(RegisterEventData data) {
        validateObject(new ValidationData<>(eventValidator, getNewEvent(data), getBindingResult(data)));
        Event newEvent = getNewEvent(data);

        setStudentGroupsInEvent(newEvent);
        setJournalPageInEvent(newEvent);

        saveEvent(newEvent);
    }

    private Event getNewEvent(RegisterEventData data) {
        return data.getNewEvent();
    }

    private BindingResult getBindingResult(RegisterEventData data) {
        return data.getBindingResult();
    }

    private void setStudentGroupsInEvent(Event event) {
        event.setStudentGroups(getExistingStudentGroups(event));
    }

    private List<StudentGroup> getExistingStudentGroups(Event event) {
        List<StudentGroup> existingStudentGroups = new ArrayList<>();

        for (StudentGroup group : getStudentGroups(event))
            existingStudentGroups.add(groupService.findOne(getGroupId(group)));

        return existingStudentGroups;
    }

    private List<StudentGroup> getStudentGroups(Event event) {
        return event.getStudentGroups();
    }

    private Long getGroupId(StudentGroup group) {
        return group.getId();
    }

    private void setJournalPageInEvent(Event event) {
        event.setPage(getExistingPage(event));
    }

    private JournalPage getExistingPage(Event event) {
        return pageRepository.findById(getPageId(event)).get();
    }

    private Long getPageId(Event event) {
        return event.getPage().getId();
    }

    private void saveEvent(Event event) {
        eventRepository.save(event);
    }

    @Override
    @Transactional
    public void update(UpdateEventData data) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, getEventId(data), eventRepository));
        validateObject(new ValidationData<>(eventValidator, getUpdatedEvent(data), getBindingResult(data)));

        Event existingEvent = findOne(getEventId(data));
        Event updatedEvent = getUpdatedEvent(data);

        setUpdatedStudentGroupsInEvent(updatedEvent, existingEvent);
        setUpdatedThemeInEvent(updatedEvent, existingEvent);
        setUpdatedEventTypeInEvent(updatedEvent, existingEvent);
        setUpdatedDescriptionInEvent(updatedEvent, existingEvent);
        setUpdatedDateInEvent(updatedEvent, existingEvent);

        saveEvent(existingEvent);
    }

    private Long getEventId(UpdateEventData data) {
        return data.getId();
    }

    private BindingResult getBindingResult(UpdateEventData data) {
        return data.getBindingResult();
    }

    private Event getUpdatedEvent(UpdateEventData data) {
        Event updatedEvent = getEvent(data);

        setIdInEvent(updatedEvent, data);

        return updatedEvent;
    }

    private Event getEvent(UpdateEventData data) {
        return data.getUpdatedEvent();
    }

    private void setIdInEvent(Event event, UpdateEventData data) {
        event.setId(getEventId(data));
    }

    private void setUpdatedStudentGroupsInEvent(Event updatedEvent, Event existingEvent) {
        existingEvent.setStudentGroups(getExistingStudentGroups(updatedEvent));
    }

    private void setUpdatedThemeInEvent(Event updatedEvent, Event existingEvent) {
        existingEvent.setEventTheme(getUpdatedEventTheme(updatedEvent));
    }

    private String getUpdatedEventTheme(Event updatedEvent) {
        return updatedEvent.getEventTheme();
    }

    private void setUpdatedEventTypeInEvent(Event updatedEvent, Event existingEvent) {
        existingEvent.setEventType(getUpdatedEventType(updatedEvent));
    }

    private EventTypeEnum getUpdatedEventType(Event updatedEvent) {
        return updatedEvent.getEventType();
    }

    private void setUpdatedDescriptionInEvent(Event updatedEvent, Event existingEvent) {
        existingEvent.setDescription(getUpdatedDescription(updatedEvent));
    }

    private String getUpdatedDescription(Event updatedEvent) {
        return updatedEvent.getDescription();
    }

    private void setUpdatedDateInEvent(Event updatedEvent, Event existingEvent) {
        existingEvent.setDate(getUpdatedDate(updatedEvent));
    }

    private LocalDate getUpdatedDate(Event updatedEvent) {
        return updatedEvent.getDate();
    }

    @Override
    @Transactional
    public void delete(Long eventId) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, eventId, eventRepository));

        eventRepository.deleteById(eventId);
    }

    @Override
    @Transactional
    public void removeStudentGroupsFromEvents(Long pageId) {
        for (Event event : findAllEvents(pageId))
            event.setStudentGroups(new ArrayList<>());
    }

    private List<Event> findAllEvents(Long pageId) {
        return eventRepository.findAllByPage_Id(pageId);
    }
}

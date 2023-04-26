package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Event;
import ua.dgma.electronicDeansOffice.models.EventData;
import ua.dgma.electronicDeansOffice.repositories.EventDataRepository;
import ua.dgma.electronicDeansOffice.repositories.EventRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.EventDataService;

@Service
@Transactional(readOnly = true)
public class EventDataServiceImpl implements EventDataService {
    private final EventDataRepository dataRepository;
    private final EventRepository eventRepository;
    private String className;

    public EventDataServiceImpl(EventDataRepository dataRepository,
                                EventRepository eventRepository) {
        this.dataRepository = dataRepository;
        this.eventRepository = eventRepository;
        this.className = EventData.class.getSimpleName();
    }

    @Override
    public EventData findOne(Long eventDataId) {
        return dataRepository.findById(eventDataId).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "id", eventDataId)));
    }

    @Override
    @Transactional
    public EventData createNewEventData(Event event) {
        EventData data = new EventData();

        setEventId(data, event);
        setSemester(data, event);
        setPageId(data, event);
        setPageName(data, event);
        setEventTheme(data, event);
        setEventDescription(data, event);
        setEventType(data, event);
        setEventDate(data, event);
        setTeacher(data, event);

        dataRepository.save(data);
        return data;
    }

    private void setEventId(EventData data, Event event) {
        data.setEventId(event.getId());
    }

    private void setSemester(EventData data, Event event) {
        data.setSemester(event.getSemester());
    }

    private void setPageId(EventData data, Event event) { data.setPageId(event.getPage().getId()); }

    private void setPageName(EventData data, Event event) { data.setPageName(event.getPage().getPageName()); }

    private void setEventTheme(EventData data, Event event) {
        data.setEventTheme(event.getEventTheme());
    }

    private void setEventDescription(EventData data, Event event) {
        data.setEventDescription(event.getDescription());
    }

    private void setEventType(EventData data, Event event) {
        data.setEventType(event.getEventType());
    }

    private void setEventDate(EventData data, Event event) {
        data.setEventDate(event.getDate());
    }

    private void setTeacher(EventData data, Event event) {
        data.setTeacher(event.getPage().getJournal().getTeacher());
    }

    @Override
    @Transactional
    public void deleteEventData(Long eventId) {
        dataRepository.deleteById(eventId);
    }
}

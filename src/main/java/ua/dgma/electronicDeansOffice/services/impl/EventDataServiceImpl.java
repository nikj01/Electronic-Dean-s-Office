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
        EventData newEventData = new EventData();

        newEventData.setEventId(event.getId());
        newEventData.setPageName(event.getPage().getPageName());
        newEventData.setEventTheme(event.getEventTheme());
        newEventData.setEventDescription(event.getDescription());
        newEventData.setEventType(event.getEventType());
        newEventData.setEventDate(event.getDate());

        dataRepository.save(newEventData);
        return newEventData;
    }

    @Override
    @Transactional
    public void deleteEventData(Long eventId) {
        dataRepository.deleteById(eventId);
    }
}

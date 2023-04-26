package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Event;
import ua.dgma.electronicDeansOffice.models.EventData;

public interface EventDataService {
    EventData findOne(Long eventDataId);
    EventData createNewEventData(Event event);
    void deleteEventData(Long eventId);
}

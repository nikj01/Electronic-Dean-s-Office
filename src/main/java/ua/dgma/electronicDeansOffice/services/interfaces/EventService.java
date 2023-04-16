package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Event;
import ua.dgma.electronicDeansOffice.services.impl.data.event.RegisterEventData;
import ua.dgma.electronicDeansOffice.services.impl.data.event.UpdateEventData;

public interface EventService {
    Event findOne(Long eventId);
    void register(RegisterEventData data);
    void update(UpdateEventData data);
    void delete(Long eventId);
    void removeStudentGroupsFromEvents(Long pageId);
}

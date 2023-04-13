package ua.dgma.electronicDeansOffice.services.impl.data.event;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Event;

@Data
@RequiredArgsConstructor
public class UpdateEventData {
    @NonNull
    private Long id;
    @NonNull
    private Event updatedEvent;
    @NonNull
    private BindingResult bindingResult;
}

package ua.dgma.electronicDeansOffice.services.impl.data.event;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Event;

@Data
@RequiredArgsConstructor
public class RegisterEventData {
    @NonNull
    private Event newEvent;
    @NonNull
    private BindingResult bindingResult;
}

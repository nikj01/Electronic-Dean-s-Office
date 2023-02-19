package ua.dgma.electronicDeansOffice.exceptions;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {
    @NonNull
    private String message;

    @NonNull
    private long timestamp;
}

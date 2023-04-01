package ua.dgma.electronicDeansOffice.exceptions;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @NonNull
    private String message;
    @NonNull
    private Long timestamp;
}

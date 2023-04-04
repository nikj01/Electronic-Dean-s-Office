package ua.dgma.electronicDeansOffice.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.nio.file.Path;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @NonNull
    private Date timestamp;
    @NonNull
    private Integer status;
    @NonNull
    private String error;
    @NonNull
    private String message;
//    @NonNull
//    private Path path;
}

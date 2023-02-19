package ua.dgma.electronicDeansOffice.utill;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ua.dgma.electronicDeansOffice.exceptions.CustomException;

import java.util.List;

public class ErrorsBuilder {

    public static void returnErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append("Incorrect ")
                    .append(error.getField().toUpperCase())
                    .append(" - ")
                    .append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                    .append(";")
                    .append(" ");
        }

        throw new CustomException(errorMsg.toString());
    }
}

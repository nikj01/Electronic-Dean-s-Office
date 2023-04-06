package ua.dgma.electronicDeansOffice.exceptions;

import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;

public class IncorrectPropertyException extends RuntimeException{

    public IncorrectPropertyException(String message) {
        super(message + " Check the correct data entered.");
    }
}

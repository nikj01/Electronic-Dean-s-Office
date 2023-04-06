package ua.dgma.electronicDeansOffice.exceptions;

import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;

public class NotFoundException extends RuntimeException {
    private final ExceptionData exceptionData;
    public NotFoundException(ExceptionData exceptionData) {
        super(exceptionData.getCopyOfClass() + " with " + exceptionData.getNameOfParam() + " " + exceptionData.getParam() + " was not found! Check the correct data entered.");
        this.exceptionData = exceptionData;
    }
}

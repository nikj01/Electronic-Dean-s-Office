package ua.dgma.electronicDeansOffice.exceptions;

import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;

public class ObjectExistsException extends RuntimeException {
    private final ExceptionData exceptionData;
    public ObjectExistsException(ExceptionData exceptionData) {
        super(exceptionData.getCopyOfClass() + " with " + exceptionData.getNameOfParam() + " " + exceptionData.getParam() + " already exists! Check the correct data entered.");
        this.exceptionData = exceptionData;
    }
}

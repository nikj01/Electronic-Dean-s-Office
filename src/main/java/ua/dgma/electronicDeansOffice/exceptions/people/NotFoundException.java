package ua.dgma.electronicDeansOffice.exceptions.people;

public class NotFoundException extends RuntimeException {

    private final ExceptionData exceptionData;

    public NotFoundException(ExceptionData exceptionData){
        super(exceptionData.getCopyOfClass() + " with " + exceptionData.getNameOfParam() + " " + exceptionData.getParam() + " was not found! Check the correct data entered.");
        this.exceptionData = exceptionData;
    }
}

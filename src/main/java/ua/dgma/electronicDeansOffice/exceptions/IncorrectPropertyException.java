package ua.dgma.electronicDeansOffice.exceptions;

public class IncorrectPropertyException extends RuntimeException{

    public IncorrectPropertyException(String message) {
        super(message + " Check the correct data entered.");
    }
}

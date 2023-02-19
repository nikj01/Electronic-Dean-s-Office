package ua.dgma.electronicDeansOffice.exceptions.PersonExceptions;

public class PersonNotFoundException extends RuntimeException {

    public <T, T1> PersonNotFoundException(T firstParam, T1 secondParam){
        super("Person with " + firstParam + " " + secondParam + " was not found! Check the correct data entered.");
    }

}

package ua.dgma.electronicDeansOffice.exceptions.student;

import ua.dgma.electronicDeansOffice.exceptions.person.PersonNotFoundException;

public class StudentNotFoundException extends PersonNotFoundException {
    public <T, T1> StudentNotFoundException(T firstParam, T1 secondParam) {
        super(firstParam, secondParam);
    }
}

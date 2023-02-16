package ua.dgma.electronicDeansOffice.exceptions.PersonExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PersonWithThisUidDoesntExist extends Exception{

    public PersonWithThisUidDoesntExist(Long uid){
        super("Person with uid:" + uid + "doesn't exist");
    }
}

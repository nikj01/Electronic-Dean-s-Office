package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Person;

public interface PeopleService<T extends Person> {

    T findOneByUid(Long uid);
}

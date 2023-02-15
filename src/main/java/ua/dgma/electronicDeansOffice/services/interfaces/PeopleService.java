package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Person;

import java.util.List;

public interface PeopleService<T, T1> {

    T1 findOneByUid(Long uid);

    T1 findOneByEmail(String email);

    T1 findOneBySurname(String surname);

    List<T1> findAll();

    void registerNewPerson(T1 person);
}

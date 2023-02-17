package ua.dgma.electronicDeansOffice.services.interfaces;

import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.PersonExceptions.PersonWithThisUidDoesntExist;

import java.util.List;

public interface PeopleService<T, T1, T2> {
    // Person, PersonGetDTO, PersonsGetDTO

    T findOnePersonByUid(Long uid);

    T findOnePersonByEmail(String email);

    T findOnePersonBySurname(String surname);

    List<T> findAllPeople();

    void registerNewPerson(T newPerson);

    void updatePersonByUidPut(Long uid, T updatedPerson);
    void updatePersonByUidPatch(Long uid, T updatedPerson);

}

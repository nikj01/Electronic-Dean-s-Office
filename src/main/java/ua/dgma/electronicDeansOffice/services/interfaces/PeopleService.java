package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;

import java.util.List;

public interface PeopleService<P extends Person> {
    P findByUid(Long uid);
    List<P> findByEmail(String email);
    List<P> findBySurname(String surname);
    List<P> findAllPeople(FindAllData data);
    void registerNew(RegisterPersonData<P> data);
    void update(UpdatePersonData<P> data);
    void delete(Long uid);
    void softDelete(Long uid);
    void softDeletePeople(List<P> people);
}

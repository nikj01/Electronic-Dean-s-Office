package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;

import java.util.List;

public interface PeopleService<P extends Person> {

    Class<P> getPersistentClass();
    P findByUid(Long uid);
    List<P> findByEmail(String email);
    List<P> findBySurname(String surname);
    List<P> findAllPeople(FindAllData data);
    void registerNew(RegisterPersonData<P> data);
    void updateByUid(UpdatePersonData<P> data);
    void deleteByUId(Long uid);
    void softDeleteByUId(Long uid);
}

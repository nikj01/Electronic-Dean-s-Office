package ua.dgma.electronicDeansOffice.services.interfaces;

import org.springframework.validation.BindingResult;

import java.util.List;

public interface PeopleService<T> {

    Class<T> getPersistentClass();

    T findByUid(Long uid);

    T findByEmail(String email);

    List<T> findBySurname(String surname);

    List<T> findAllWithPaginationOrWithout(Integer page, Integer peoplePerPage);

    void registerNew(T t, BindingResult bindingResult);

    void updateByUid(Long uid, T t, BindingResult bindingResult);

    void deleteByUId(Long uid);

    void validatePerson(T t, BindingResult bindingResult);

    void checkExistsWithSuchUid(Long uid);

    void checkExistsWithSuchSurname(String surname);

}

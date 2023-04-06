package ua.dgma.electronicDeansOffice.services.interfaces;

import org.springframework.validation.BindingResult;

import java.util.List;

public interface PeopleService<T> {

    Class<T> getPersistentClass();
    T findByUid(Long uid);
    List<T> findByEmail(String email);
    List<T> findBySurname(String surname);
    List<T> findAllPeople(Integer page, Integer peoplePerPage, Boolean isDeleted, String facultyName);
    List<T> findAllWithPaginationOrWithout(Integer page, Integer peoplePerPage, Boolean isDeleted);
    List<T> findAllWithPaginationOrWithoutByFaculty(Integer page, Integer peoplePerPage, Boolean isDeleted, String faculutyName);
    void registerNew(T t, BindingResult bindingResult);
    void updateByUid(Long uid, T t, BindingResult bindingResult);
    void deleteByUId(Long uid);
    void softDeleteByUId(Long uid);
    void checkExistsWithSuchSurname(String surname);

}

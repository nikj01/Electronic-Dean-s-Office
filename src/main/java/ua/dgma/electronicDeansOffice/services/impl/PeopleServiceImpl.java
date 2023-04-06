package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.findAllByFacultyData;
import ua.dgma.electronicDeansOffice.services.specifications.PeopleSpecifications;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkPaginationParameters;

@Service
@Transactional(readOnly = true)
public abstract class PeopleServiceImpl<P extends Person> implements PeopleService<P> {

    private final PeopleRepository<P> repository;
    private final Validator validator;
    private final ExceptionData exceptionData;
    private final PeopleSpecifications specifications;
    private Class<P> persistentClass;

    @Autowired
    protected PeopleServiceImpl(PeopleRepository<P> repository,
                                Validator validator,
                                ExceptionData exceptionData,
                                PeopleSpecifications specifications) {
        this.repository = repository;
        this.validator = validator;
        this.exceptionData = exceptionData;
        this.specifications = specifications;
    }

    /*  This method defines class that is required for the ExceptionData in the future */
    @Override
    public Class<P> getPersistentClass() {
        return persistentClass = (Class<P>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public P findByUid(Long uid) {
        return repository.getByUid(uid).orElseThrow(() -> new NotFoundException(new ExceptionData<>(getPersistentClass().getSimpleName(), "uid", uid)));
    }

    @Override
    public List<P> findByEmail(String email) {
        List<P> people = new ArrayList<>();

        people.add(repository.getByEmail(email).orElseThrow(() -> new NotFoundException(new ExceptionData<>(getPersistentClass().getSimpleName(), "email", email))));

        return people;
    };

    @Override
    public List<P> findBySurname(String surname) {
        checkExistsWithSuchSurname(surname);
        return repository.getBySurname(surname);
    }

    @Override
    public List<P> findAllPeople(Integer page,
                                 Integer peoplePerPage,
                                 Boolean isDeleted,
                                 String facultyName) {
        if(facultyName == null)
            return findAllWithPaginationOrWithout(page, peoplePerPage, isDeleted);
        else
            return findAllWithPaginationOrWithoutByFaculty(page, peoplePerPage, isDeleted, facultyName);
    }

    @Override
    public List<P> findAllWithPaginationOrWithout(Integer page,
                                                  Integer peoplePerPage,
                                                  Boolean isDeleted) {
        if(checkPaginationParameters(page, peoplePerPage))
            return repository.findAll(Specification.where(specifications.getObjectByDeletedCriteria(isDeleted)));
        else
            return repository.findAll(Specification.where(specifications.getObjectByDeletedCriteria(isDeleted)), PageRequest.of(page, peoplePerPage)).getContent();
    }

    @Override
    public abstract List<P> findAllWithPaginationOrWithoutByFaculty(Integer page,
                                                           Integer peoplePerPage,
                                                           Boolean isDeleted,
                                                           String facultyName);


    @Override
    @Transactional
    public abstract void registerNew(P p, BindingResult bindingResult);

    @Override
    @Transactional
    public abstract void updateByUid(Long uid, P p, BindingResult bindingResult);

    @Override
    @Transactional
    public abstract void deleteByUId(Long uid);

    @Override
    @Transactional
    public void softDeleteByUId(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(getPersistentClass().getSimpleName(), uid, repository));

        P person = findByUid(uid);
        person.setDeleted(true);

        repository.save(person);
    }

    @Override
    public void checkExistsWithSuchSurname(String surname) {
        if(!repository.existsBySurname(surname)) throw new NotFoundException(new ExceptionData<>(getPersistentClass().getSimpleName(), "surname", surname));
    }
}

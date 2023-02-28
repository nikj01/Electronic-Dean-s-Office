package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.exceptions.people.ExceptionData;
import ua.dgma.electronicDeansOffice.exceptions.people.PersonNotFoundException;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ErrorsBuilder.returnErrorsToClient;

@Service
@Transactional(readOnly = true)
public abstract class PeopleServiceImpl<P extends Person> implements PeopleService<P> {

    private final PeopleRepository<P> repository;
    private final Validator validator;
    private final ExceptionData exceptionData;
    private Class<P> persistentClass;

    @Autowired
    protected PeopleServiceImpl(PeopleRepository<P> repository,
                                Validator validator,
                                ExceptionData exceptionData) {
        this.repository = repository;
        this.validator = validator;
        this.exceptionData = exceptionData;
    }

    /*  This method defines class that is required for the ExceptionData in the future */
    @Override
    public Class<P> getPersistentClass() {
        return persistentClass = (Class<P>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public P findByUid(Long uid) {
        return repository.getByUid(uid).orElseThrow(() -> new PersonNotFoundException(new ExceptionData<Long>(getPersistentClass().getSimpleName(), "uid", uid)));
    }

    @Override
    public P findByEmail(String email) {
        return repository.getByEmail(email).orElseThrow(() -> new PersonNotFoundException(new ExceptionData<String>(getPersistentClass().getSimpleName(), "email", email)));
    };

    @Override
    public List<P> findBySurname(String surname) {
        checkExistsWithSuchSurname(surname);
        return repository.getBySurname(surname);
    }

    @Override
    public List<P> findAllWithPaginationOrWithout(Integer page, Integer peoplePerPage) {
        if(checkPaginationParameters(page, peoplePerPage))
            return repository.findAll();
        else
            return repository.findAll(PageRequest.of(page, peoplePerPage)).getContent();
    }

    /*
     * It mb will be abstract
     * */
    @Override
    @Transactional
    public void registerNew(P p, BindingResult bindingResult) {
        validatePerson(p, bindingResult);
        repository.save(p);
    }
    @Override
    @Transactional
    public abstract void updateByUid(Long uid, P p, BindingResult bindingResult);

    /*
     * It mb will be abstract
     * */
    @Override
    @Transactional
    public void deleteByUId(Long uid) {
        checkExistsWithSuchUid(uid);
        repository.deleteByUid(uid);
    }

    @Override
    public void validatePerson(P person, BindingResult bindingResult) {
        validator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
    }

    @Override
    public void checkExistsWithSuchUid(Long uid) {
        if(!repository.existsByUid(uid)) throw new PersonNotFoundException(new ExceptionData<Long>(getPersistentClass().getSimpleName(), "uid", uid));
    }

    @Override
    public void checkExistsWithSuchSurname(String surname) {
        if(!repository.existsBySurname(surname)) throw new PersonNotFoundException(new ExceptionData<String>(getPersistentClass().getSimpleName(), "surname", surname));
    }

    @Override
    public boolean checkPaginationParameters(Integer page, Integer peoplePerPage) {
        return page == null || peoplePerPage == null;
    }
}

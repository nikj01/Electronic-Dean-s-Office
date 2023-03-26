package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.exceptions.ExceptionData;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.models.Person;
//import ua.dgma.electronicDeansOffice.models.QPerson;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.specifications.PeopleSpecifications;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkPaginationParameters;

@Service
@Transactional(readOnly = true)
public abstract class PeopleServiceImpl<P extends Person> implements PeopleService<P> {

    private final PeopleRepository<P> repository;
    private final Validator validator;
    private final ExceptionData exceptionData;
    private final PeopleSpecifications<P> specifications;

//    private final PersonSpecifications<P> spec;
//    private Root root;
//    private CriteriaQuery query;
//    private CriteriaBuilder builder;

    private Class<P> persistentClass;

    @Autowired
    protected PeopleServiceImpl(PeopleRepository<P> repository,
                                Validator validator,
                                ExceptionData exceptionData,
                                PeopleSpecifications<P> specifications) {
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
    public P findByEmail(String email) {
        return repository.getByEmail(email).orElseThrow(() -> new NotFoundException(new ExceptionData<>(getPersistentClass().getSimpleName(), "email", email)));
    };

    @Override
    public List<P> findBySurname(String surname) {
        checkExistsWithSuchSurname(surname);
        return repository.getBySurname(surname);
    }

    @Override
    public List<P> findAllWithPaginationOrWithout(Integer page, Integer peoplePerPage, Boolean isDeleted) {
        if(checkPaginationParameters(page, peoplePerPage))
            return repository.findAll(specifications.getPeopleByDeletedCriteria(isDeleted));
        else
            return repository.findAll(specifications.getPeopleByDeletedCriteria(isDeleted), PageRequest.of(page, peoplePerPage)).getContent();
    }

    /*
     * It mb will be abstract
     * */
    @Override
    @Transactional
    public void registerNew(P p, BindingResult bindingResult) {
        validateObject(new ValidationData<>(validator, p, bindingResult));
        p.setDeleted(false);
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
        checkExistsWithSuchID(new CheckExistsByIdData<>(getPersistentClass().getSimpleName(), uid, repository));
        repository.deleteByUid(uid);
    }

    @Override
    @Transactional
    public void softDeleteByUId(Long uid) {
        P person = findByUid(uid);

        person.setDeleted(true);

        repository.save(person);
    }

    @Override
    public void checkExistsWithSuchSurname(String surname) {
        if(!repository.existsBySurname(surname)) throw new NotFoundException(new ExceptionData<>(getPersistentClass().getSimpleName(), "surname", surname));
    }
}

package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.specifications.PeopleSpecifications;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkPaginationParameters;

@Service
@Transactional(readOnly = true)
public abstract class PeopleServiceImpl<P extends Person> implements PeopleService<P> {

    private final PeopleRepository<P> repository;
    private final Validator validator;
    private final ExceptionData exceptionData;
    private final PeopleSpecifications specifications;
    private Class<P> persistentClass;
    private String className;

    @Autowired
    protected PeopleServiceImpl(PeopleRepository<P> repository,
                                Validator validator,
                                ExceptionData exceptionData,
                                PeopleSpecifications specifications) {
        this.repository = repository;
        this.validator = validator;
        this.exceptionData = exceptionData;
        this.specifications = specifications;
        this.className = getPersistentClass().getSimpleName();
    }

    /*  This method defines class that is required for the ExceptionData in the future */
    @Override
    public Class<P> getPersistentClass() {
        return persistentClass = (Class<P>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public P findByUid(Long uid) {
        return repository.getByUid(uid).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "uid", uid)));
    }

    @Override
    public List<P> findByEmail(String email) {
        return repository.getByEmailContainingIgnoreCase(email).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "email", email)));
    };

    @Override
    public List<P> findBySurname(String surname) {
        return repository.getBySurnameContainingIgnoreCase(surname).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "surname", surname)));
    }

    @Override
    public List<P> findAllPeople(FindAllData data) {
        if(data.getFacultyName() == null)
            return findAllWithPaginationOrWithout(data);
        else
            return findAllWithPaginationOrWithoutByFaculty(data);
    }

    public List<P> findAllWithPaginationOrWithout(FindAllData data) {
        if(checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return repository.findAll(Specification.where(specifications.getObjectByDeletedCriteria(data.getDeleted())), Sort.by("surname"));
        else
            return repository.findAll(Specification.where(specifications.getObjectByDeletedCriteria(data.getDeleted())), PageRequest.of(data.getPage(), data.getObjectsPerPage(), Sort.by("surname"))).getContent();
    }

    public abstract List<P> findAllWithPaginationOrWithoutByFaculty(FindAllData data);


    @Override
    @Transactional
    public abstract void registerNew(RegisterPersonData<P> data);

    @Override
    @Transactional
    public abstract void updateByUid(UpdatePersonData<P> data);

    @Override
    @Transactional
    public abstract void deleteByUId(Long uid);

    @Override
    @Transactional
    public abstract void softDeleteByUId(Long uid);

    public void checkExistsWithSuchSurname(String surname) {
        if(!repository.existsBySurname(surname)) throw new NotFoundException(new ExceptionData<>(className, "surname", surname));
    }
}

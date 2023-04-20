package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkPaginationParameters;

@Service
@Transactional(readOnly = true)
public abstract class PeopleServiceImpl<P extends Person> implements PeopleService<P> {
    private final PeopleRepository<P> repository;
    private final PeopleSpecifications specifications;
    private Class<P> persistentClass;
    private String className;

    @Autowired
    protected PeopleServiceImpl(PeopleRepository<P> repository,
                                PeopleSpecifications specifications) {
        this.repository = repository;
        this.specifications = specifications;
        this.className = getPersistentClass().getSimpleName();
    }

    /*  This method defines class that is required for the ExceptionData in the future */
    private Class<P> getPersistentClass() {
        return persistentClass = (Class<P>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public P findByUid(Long uid) {
        return repository.getByUid(uid).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "uid", uid)));
    }

    @Override
    public List<P> findByEmail(String email) {
        return repository.getByEmailContainingIgnoreCase(email).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "email", email)));
    }

    @Override
    public List<P> findBySurname(String surname) {
        return repository.getBySurnameContainingIgnoreCase(surname).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "surname", surname)));
    }

    @Override
    public List<P> findAllPeople(FindAllData data) {
        if (checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return findAllWithSpec(getSpec(data));
        else
            return findAllWithSpecAndPagination(getSpec(data), data);
    }

    protected List<P> findAllWithSpec(Specification spec) {
        return repository.findAll(spec, Sort.by("surname"));
    }

    protected List<P> findAllWithSpecAndPagination(Specification spec, FindAllData data) {
        return repository.findAll(spec, PageRequest.of(data.getPage(), data.getObjectsPerPage(), Sort.by("surname"))).getContent();
    }

    protected abstract Specification getSpec(FindAllData data);

    @Override
    @Transactional
    public abstract void register(RegisterPersonData<P> data);

    protected Long getPersonUid(RegisterPersonData data) {
        return data.getNewPerson().getUid().longValue();
    }
    protected P getNewPerson(RegisterPersonData data) {
        return (P) data.getNewPerson();
    }

    protected void savePerson(P person) {
        repository.save(person);
    }

    @Override
    @Transactional
    public abstract void update(UpdatePersonData<P> data);

    protected void setIdInUpdatedPerson(P person, UpdatePersonData data) {
        person.setUid(data.getUid());
    }

    @Override
    @Transactional
    public abstract void delete(Long uid);

    @Override
    @Transactional
    public abstract void softDelete(Long uid);

    protected P markPersonAsDeleted(P person) {
        person.setDeleted(true);
        return person;
    }

    public void checkExistsWithSuchSurname(String surname) {
        if (!repository.existsBySurname(surname))
            throw new NotFoundException(new ExceptionData<>(className, "surname", surname));
    }
}

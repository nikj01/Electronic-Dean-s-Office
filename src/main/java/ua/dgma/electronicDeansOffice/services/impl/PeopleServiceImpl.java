package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.exceptions.ExceptionData;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.findAllByFacultyData;
import ua.dgma.electronicDeansOffice.services.specifications.Specifications;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;

import javax.persistence.criteria.Predicate;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
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
    private final Specifications<P> specifications;
    private Class<P> persistentClass;

    @Autowired
    protected PeopleServiceImpl(PeopleRepository<P> repository,
                                Validator validator,
                                ExceptionData exceptionData,
                                Specifications<P> specifications) {
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
    public List<P> findAllWithPaginationOrWithoutByFaculty(Integer page,
                                                           Integer peoplePerPage,
                                                           Boolean isDeleted,
                                                           String facultyName) {

        List<P> newList = new ArrayList<>();

        switch (getPersistentClass().getSimpleName()) {
            case ("DeaneryWorker"): {
                Specification spec = Specification.where(specifications.getObjectByDeletedCriteria(isDeleted).and(specifications.findDeaneryWorkersByFacultyCriteria(facultyName)));
                for(P person: findAllByFaculty(new findAllByFacultyData(page, peoplePerPage,isDeleted,facultyName, spec))){
                    newList.add(person);
                }
                return newList;
            }
            case ("Teacher"): {
                Specification spec = Specification.where(specifications.getObjectByDeletedCriteria(isDeleted).and(specifications.findTeachersByFacultyCriteria(facultyName)));
                for(P person: findAllByFaculty(new findAllByFacultyData(page, peoplePerPage,isDeleted,facultyName, spec))){
                    newList.add(person);
                }
                return newList;
            }
            case ("Student"): {
                Specification spec = Specification.where(specifications.getObjectByDeletedCriteria(isDeleted).and(specifications.findStudentsByFacultyCriteria(facultyName)));
                for(P person: findAllByFaculty(new findAllByFacultyData(page, peoplePerPage,isDeleted,facultyName, spec))){
                    newList.add(person);
                }
                return newList;
            }
        }
        return newList;
    }

    public List<P> findAllByFaculty(findAllByFacultyData data) {

        if(checkPaginationParameters(data.getPage(), data.getPeoplePerPage()))
            return repository.findAll(data.getSpecification());
        else
            return repository.findAll(data.getSpecification(), PageRequest.of(data.getPage(), data.getPeoplePerPage())).getContent();
    }

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

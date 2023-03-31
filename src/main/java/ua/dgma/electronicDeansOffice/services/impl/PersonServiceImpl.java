package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Person;
//import ua.dgma.electronicDeansOffice.models.QPerson;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.specifications.Specifications;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.PeopleValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkPaginationParameters;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl extends PeopleServiceImpl<Person> {

    private final PeopleRepository<Person> personRepository;
    private final PeopleValidator personValidator;
    private final Specifications<Person> specifications;

    @Autowired
    protected PersonServiceImpl(PeopleRepository<Person> personRepository,
                                ExceptionData exceptionData,
                                PeopleValidator personValidator,
                                Specifications<Person> specifications) {
        super(personRepository, personValidator, exceptionData, specifications);
        this.personRepository = personRepository;
        this.personValidator = personValidator;
        this.specifications = specifications;
    }

    @Override
    public void registerNew(Person person, BindingResult bindingResult) {
        validateObject(new ValidationData<>(personValidator, person, bindingResult));
        personRepository.save(person);
    }

    @Override
    public void updateByUid(Long uid, Person updatedPerson, BindingResult bindingResult) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(Person.class.getSimpleName(), uid, personRepository));
        validateObject(new ValidationData<>(personValidator , updatedPerson, bindingResult));

        updatedPerson.setUid(uid);

        personRepository.save(updatedPerson);
    }

    @Override
    public void deleteByUId(Long uid) {
        personRepository.deleteByUid(uid);
    }

}

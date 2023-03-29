package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl extends PeopleServiceImpl<Person> {

    private final PeopleRepository<Person> personRepository;
    private final PeopleValidator personValidator;

    @Autowired
    protected PersonServiceImpl(PeopleRepository<Person> personRepository,
                                ExceptionData exceptionData,
                                PeopleValidator personValidator,
                                Specifications<Person> specifications) {
        super(personRepository, personValidator, exceptionData, specifications);
        this.personRepository = personRepository;
        this.personValidator = personValidator;
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

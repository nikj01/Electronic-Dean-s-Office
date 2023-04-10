package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.*;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.specifications.PeopleSpecifications;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;

import java.util.ArrayList;
import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceByIDBeforeRegistration;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl extends PeopleServiceImpl<Person> {

    private final PeopleRepository<Person> personRepository;
    private final Validator personValidator;
    private final PeopleSpecifications specifications;
    private final PeopleService<DeaneryWorker> deaneryWorkerService;
    private final PeopleService<Teacher> teacherService;
    private final PeopleService<Student> studentService;
    private String className;

    @Autowired
    protected PersonServiceImpl(PeopleRepository<Person> personRepository,
                                ExceptionData exceptionData,
                                Validator personValidator,
                                PeopleSpecifications specifications,
                                PeopleService<DeaneryWorker> deaneryWorkerService,
                                PeopleService<Teacher> teacherService,
                                PeopleService<Student> studentService) {
        super(personRepository, personValidator, exceptionData, specifications);
        this.personRepository = personRepository;
        this.personValidator = personValidator;
        this.specifications = specifications;
        this.deaneryWorkerService = deaneryWorkerService;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.className = Person.class.getSimpleName();
    }

    @Override
    public List<Person> findAllWithPaginationOrWithoutByFaculty(FindAllData data) {
        List<Person> peopleAtFaculty = new ArrayList<>();
        List<DeaneryWorker> deaneryWorkers = deaneryWorkerService.findAllPeople(data);
        List<Teacher> teachers = teacherService.findAllPeople(data);
        List<Student> students = studentService.findAllPeople(data);

        peopleAtFaculty.addAll(deaneryWorkers);
        peopleAtFaculty.addAll(teachers);
        peopleAtFaculty.addAll(students);

        return peopleAtFaculty;
    }

    @Override
    public void registerNew(RegisterPersonData data) {
        checkExistenceByIDBeforeRegistration(new CheckExistsByIdData<>(className, data.getNewPerson().getUid().longValue(), personRepository));
        personRepository.save(data.getNewPerson());
    }

    @Override
    public void updateByUid(UpdatePersonData data) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, data.getUid(), personRepository));

        Person updatedPerson = data.getUpdatedPerson();
        updatedPerson.setUid(data.getUid());

        personRepository.save(updatedPerson);
    }

    @Override
    public void deleteByUId(Long uid) {
        personRepository.deleteByUid(uid);
    }

    @Override
    public void softDeleteByUId(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, uid, personRepository));

        Person person = findByUid(uid);
        person.setDeleted(true);

        personRepository.save(person);
    }

    @Override
    public void markPeopleAsDeleted(List<Person> people) {

    }
}

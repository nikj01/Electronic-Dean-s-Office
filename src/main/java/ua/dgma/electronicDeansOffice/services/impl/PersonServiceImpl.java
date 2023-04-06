package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
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
    }

    @Override
    public List<Person> findAllWithPaginationOrWithoutByFaculty(Integer page, Integer peoplePerPage, Boolean isDeleted, String facultyName) {
        List<Person> peopleAtFaculty = new ArrayList<>();
        List<DeaneryWorker> deaneryWorkers = deaneryWorkerService.findAllWithPaginationOrWithoutByFaculty(null, null, isDeleted, facultyName);
        List<Teacher> teachers = teacherService.findAllWithPaginationOrWithoutByFaculty(null, null, isDeleted, facultyName);
        List<Student> students = studentService.findAllWithPaginationOrWithoutByFaculty(null, null, isDeleted, facultyName);

        peopleAtFaculty.addAll(deaneryWorkers);
        peopleAtFaculty.addAll(teachers);
        peopleAtFaculty.addAll(students);

        return peopleAtFaculty;
    }

    @Override
    public void registerNew(Person person, BindingResult bindingResult) {
        checkExistenceByIDBeforeRegistration(new CheckExistsByIdData<>(Person.class.getSimpleName(), person.getUid().longValue(), personRepository));
//        validateObject(new ValidationData<>(personValidator, person, bindingResult));
        personRepository.save(person);
    }

    @Override
    public void updateByUid(Long uid, Person updatedPerson, BindingResult bindingResult) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(Person.class.getSimpleName(), uid, personRepository));
//        validateObject(new ValidationData<>(personValidator , updatedPerson, bindingResult));

        updatedPerson.setUid(uid);

        personRepository.save(updatedPerson);
    }

    @Override
    public void deleteByUId(Long uid) {
        personRepository.deleteByUid(uid);
    }

}

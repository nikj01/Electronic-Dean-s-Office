package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.specifications.PeopleSpecifications;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceByIDBeforeRegistration;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl extends PeopleServiceImpl<Person> {
    private final PeopleRepository<Person> personRepository;
    private final PeopleService<DeaneryWorker> deaneryWorkerService;
    private final PeopleService<Teacher> teacherService;
    private final PeopleService<Student> studentService;
    private final PeopleSpecifications specifications;
    private String className;

    @Autowired
    protected PersonServiceImpl(PeopleRepository<Person> personRepository,
                                PeopleSpecifications specifications,
                                PeopleService<DeaneryWorker> deaneryWorkerService,
                                PeopleService<Teacher> teacherService,
                                PeopleService<Student> studentService) {
        super(personRepository, specifications);
        this.personRepository = personRepository;
        this.deaneryWorkerService = deaneryWorkerService;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.specifications = specifications;
        this.className = Person.class.getSimpleName();
    }

//    @Override
//    public List<Person> findAllWithPaginationOrWithoutByFaculty(FindAllData data) {
//        List<Person> peopleAtFaculty = new ArrayList<>();
//
//        peopleAtFaculty.addAll(getDeaneryWorkers(data));
//        peopleAtFaculty.addAll(getTeachers(data));
//        peopleAtFaculty.addAll(getStudents(data));
//
//        return peopleAtFaculty;
//    }

    @Override
    protected Specification getSpec(FindAllData data) {
        return Specification.where(specifications.getObjectByDeletedCriteria(data.getDeleted()));
    }

//    private List<DeaneryWorker> getDeaneryWorkers(FindAllData data) {
//        return deaneryWorkerService.findAllPeople(data);
//    }
//
//    private List<Teacher> getTeachers(FindAllData data) {
//        return teacherService.findAllPeople(data);
//    }
//
//    private List<Student> getStudents(FindAllData data) {
//        return studentService.findAllPeople(data);
//    }

    @Override
    public void registerNew(RegisterPersonData data) {
        checkExistenceByIDBeforeRegistration(new CheckExistsByIdData<>(className, data.getNewPerson().getUid().longValue(), personRepository));

        savePerson(data.getNewPerson());
    }

    @Override
    public void update(UpdatePersonData data) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, data.getUid(), personRepository));

        Person updatedPerson = data.getUpdatedPerson();
        SetIdInUpdatedPerson(updatedPerson, data);

        savePerson(updatedPerson);
    }

    @Override
    public void delete(Long uid) {
        personRepository.deleteByUid(uid);
    }

    @Override
    public void softDelete(Long uid) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, uid, personRepository));

        savePerson(markPersonAsDeleted(findByUid(uid)));
    }

    @Override
    public void softDeletePeople(List<Person> people) {
        for (Person person : people)
            softDelete(person.getUid());
    }
}

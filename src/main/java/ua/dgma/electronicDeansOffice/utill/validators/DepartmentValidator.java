package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.exceptions.IncorrectPropertyException;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Component
public class DepartmentValidator implements AbstractValidator {
    private final FacultyRepository facultyRepository;
    private final PeopleRepository<Person> peopleRepository;

    @Autowired
    public DepartmentValidator(FacultyRepository facultyRepository,
                               PeopleRepository<Person> peopleRepository) {
        this.facultyRepository = facultyRepository;
        this.peopleRepository = peopleRepository;
    }


    @Override
    public void validate(Object target) {
        Department department = (Department) target;

        if (checkExistenceOfTheDepartment(department)) {
            checkExistenceOfTheFaculty(department);
        } else {
            checkExistenceOfTheFaculty(department);
            checkExistenceOfTheTeachers(department);
        }
    }

    private boolean checkExistenceOfTheDepartment(Department department) {
        if(department.getId() != null) return true;
        else return false;
    }

    private void checkExistenceOfTheFaculty(Department department) {
        if(!findFaculty(department).isPresent())
            throw new IncorrectPropertyException(
                    "Faculty with name " + getFacultyName(department) +
                    " and Id " + getFacultyId(department) + " does not exist!");
    }

    private Optional<Faculty> findFaculty(Department department) {
        return facultyRepository.findById(getFacultyId(department));
    }

    private Long getFacultyId(Department department) {
        return getFacultyFromDepartment(department).getId();
    }

    private String getFacultyName(Department department) {
        return getFacultyFromDepartment(department).getName();
    }

    private Faculty getFacultyFromDepartment(Department department) {
        return department.getFaculty();
    }

    private void checkExistenceOfTheTeachers(Department department) {
        if (getTeachers(department) != null)
            for (Teacher teacher : getTeachers(department))
                checkTeacherByUid(teacher);
    }

    private List<Teacher> getTeachers(Department department) {
        return department.getTeachers();
    }

    private void checkTeacherByUid(Teacher teacher) {
        if (findPerson(teacher).isPresent())
            throw new IncorrectPropertyException("Person with uid " + getTeacherId(teacher) + " already exists!");
    }

    private Optional<Person> findPerson(Teacher teacher) {
        return peopleRepository.findById(getTeacherId(teacher));
    }

    private Long getTeacherId(Teacher teacher) {
        return teacher.getUid();
    }
}

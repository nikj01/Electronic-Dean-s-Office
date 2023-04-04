package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.exceptions.IncorrectPropertyException;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.repositories.PeopleRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByNameData;
import ua.dgma.electronicDeansOffice.utill.validators.data.DepartmentValidationData;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchName;

@Component
public class DepartmentValidator implements AbstractValidator {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final TeacherRepository teacherRepository;
    private final PeopleRepository<Person> peopleRepository;

    @Autowired
    public DepartmentValidator(DepartmentRepository departmentRepository,
                               FacultyRepository facultyRepository,
                               TeacherRepository teacherRepository,
                               PeopleRepository<Person> peopleRepository) {
        this.departmentRepository = departmentRepository;
        this.facultyRepository = facultyRepository;
        this.teacherRepository = teacherRepository;
        this.peopleRepository = peopleRepository;
    }


    @Override
    public void validate(Object target) {
        Department department = (Department) target;
        DepartmentValidationData validationData = new DepartmentValidationData(department, departmentRepository, facultyRepository, peopleRepository);

        if(checkExistenceOfTheDepartment(validationData)) {
            checkExistenceOfTheFaculty(validationData);
        } else {
            checkExistenceOfTheFaculty(validationData);
            checkExistenceOfTheTeachers(validationData);
        }
    }

    private boolean checkExistenceOfTheDepartment(DepartmentValidationData data) {
        if(data.getDepartmentRepository().getByName(data.getDepartment().getName()).isPresent()) return true; else return false;
    }

    private void checkExistenceOfTheFaculty(DepartmentValidationData data) {
        if(!data.getFacultyRepository().getByName(data.getDepartment().getFaculty().getName()).isPresent())
            throw new IncorrectPropertyException("Faculty with name " + data.getDepartment().getFaculty().getName() + " does not exist!");
    }

    private void checkExistenceOfTheTeachers(DepartmentValidationData data) {
        List<Teacher> newTeachers = data.getDepartment().getTeachers();

        for (Teacher teacher : newTeachers) {
            checkTeacherByUid(data, teacher);
        }
    }

    private void checkTeacherByUid(DepartmentValidationData data, Teacher teacher) {
        if(data.getPeopleRepository().getByUid(teacher.getUid()).isPresent())
            throw new IncorrectPropertyException("Person with uid " + teacher.getUid() + " already exists!" );
    }
}

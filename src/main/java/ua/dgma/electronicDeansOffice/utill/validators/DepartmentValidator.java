package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.repositories.functional.GetFacultyByNameInterface;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByNameData;

import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchName;

@Component
public class DepartmentValidator implements Validator {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final TeacherRepository teacherRepository;
    private final GetFacultyByNameInterface getFaculty;
    private final FacultyValidator facultyValidator;

    @Autowired
    public DepartmentValidator(DepartmentRepository departmentRepository,
                               FacultyRepository facultyRepository,
                               TeacherRepository teacherRepository,
                               GetFacultyByNameInterface getFaculty,
                               FacultyValidator facultyValidator) {
        this.departmentRepository = departmentRepository;
        this.facultyRepository = facultyRepository;
        this.teacherRepository = teacherRepository;
        this.getFaculty = getFaculty;
        this.facultyValidator = facultyValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {

        return Department.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Department department = (Department) target;

        if(departmentRepository.getByName(department.getName()).isPresent())
            errors.rejectValue("name", "Department with NAME " + department.getName() + " already exists!");
        if(!department.getTeachers().isEmpty())
            for (Teacher teacher: department.getTeachers()) {
//                teacherValidator.validate(teacher, errors);
                if(teacherRepository.getByUid(teacher.getUid()).isPresent())
                    errors.rejectValue("uid", "Teacher with UID " + teacher.getUid() + " already exists!");
                if(teacherRepository.getByEmail(teacher.getEmail()).isPresent())
                    errors.rejectValue("email", "Teacher with EMAIL " + teacher.getEmail() + " already exists!");
            }

        checkExistsWithSuchName(new CheckExistsByNameData<>(Faculty.class.getSimpleName(), department.getFaculty().getName(), facultyRepository));
    }
}

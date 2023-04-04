package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;

@Component
public class TeacherValidator implements Validator {

    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public TeacherValidator(TeacherRepository teacherRepository,
                            DepartmentRepository departmentRepository) {
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Teacher.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Teacher teacher = (Teacher) target;
        
        if(teacherRepository.getByUid(teacher.getUid()).isPresent())
            errors.rejectValue("uid", "Teacher with UID " + teacher.getUid() + " already exists!");
        if(teacherRepository.getByEmail(teacher.getEmail()).isPresent())
            errors.rejectValue("email", "Teacher with EMAIL " + teacher.getEmail() + " already exists!");
//        if(!departmentRepository.getByName(teacher.getDepartment().getName()).isPresent())
//            errors.rejectValue("department", "Department " + teacher.getDepartment().getName() + " does not exist!");
    }
}

package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;

@Component
public class StudentGroupValidator implements Validator {

    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentGroupValidator(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return StudentGroup.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentGroup studentGroup = (StudentGroup) target;

        if(studentGroupRepository.getByName(studentGroup.getName()).isPresent())
            errors.rejectValue("name", "Student group with NAME " + studentGroup.getName() + " already exists!" );
        if(studentGroupRepository.getByGroupLeader_Name(studentGroup.getGroupLeader().getName()).isPresent())
            errors.rejectValue("group_leader", "Student group with GROUP_LEADER " + studentGroup.getGroupLeader().getName() + " already exists!" );
        if(studentGroupRepository.getByDepartment_Name(studentGroup.getDepartment().getName()).isPresent())
            errors.rejectValue("department", "This group already exists in the DEPARTMENT " + studentGroup.getDepartment().getName() + "!");
    }
}

package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;

@Component
public class StudentGroupValidator implements Validator {

    private final StudentGroupRepository studentGroupRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentGroupValidator(StudentGroupRepository studentGroupRepository,
                                 DepartmentRepository departmentRepository,
                                 StudentRepository studentRepository) {
        this.studentGroupRepository = studentGroupRepository;
        this.departmentRepository = departmentRepository;
        this.studentRepository = studentRepository;
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
        if(!departmentRepository.getByName(studentGroup.getDepartment().getName()).isPresent())
            errors.rejectValue("department", "Department with NAME " + studentGroup.getDepartment().getName() + " does not exist!");
        if(studentGroup.getGroupLeader() != null)
            if(studentGroupRepository.getByGroupLeader_Uid(studentGroup.getGroupLeader().getUid().longValue()).isPresent())
                errors.rejectValue("group_leader", "Student " + studentGroup.getGroupLeader().getSurname() + " " + studentGroup.getGroupLeader().getName() + " " + studentGroup.getGroupLeader().getPatronymic() + " is already group leader in a group " + studentGroupRepository.getByGroupLeader_Uid(studentGroup.getGroupLeader().getUid()) + " !");




        //            if(studentGroupRepository.getByName(studentGroup.getName()).get().getStudents().contains(studentGroup.getGroupLeader()))
//                    group -> group.getStudents().contains(studentGroup.getGroupLeader())));
//            if(studentGroupRepository.getByGroupLeader_Uid(studentGroup.getGroupLeader().getUid().longValue()).isPresent())
//            if(studentRepository.(studentGroup.getGroupLeader().getSurname(), studentGroup.getGroupLeader().getName(), studentGroup.getGroupLeader().getPatronymic(), studentGroup.getGroupLeader().getStudentGroup().isDeleted()).isPresent())
//                errors.rejectValue("group_leader", "Student " + studentGroup.getGroupLeader().getSurname() + " " + studentGroup.getGroupLeader().getName() + " " + studentGroup.getGroupLeader().getPatronymic() + " is already group leader in a group " + studentGroupRepository.getByGroupLeader(studentGroup.getGroupLeader().getUid()) + " !");
    }
}

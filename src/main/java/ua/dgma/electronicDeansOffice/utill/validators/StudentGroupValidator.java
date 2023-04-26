package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.exceptions.IncorrectPropertyException;
import ua.dgma.electronicDeansOffice.models.*;
import ua.dgma.electronicDeansOffice.repositories.*;

import java.util.List;
import java.util.Optional;

@Component
public class StudentGroupValidator implements AbstractValidator {
    private final StudentGroupRepository studentGroupRepository;
    private final DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final PeopleRepository<Person> peopleRepository;

    @Autowired
    public StudentGroupValidator(StudentGroupRepository studentGroupRepository,
                                 DepartmentRepository departmentRepository,
                                 TeacherRepository teacherRepository,
                                 StudentRepository studentRepository,
                                 PeopleRepository<Person> peopleRepository) {
        this.studentGroupRepository = studentGroupRepository;
        this.departmentRepository = departmentRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.peopleRepository = peopleRepository;
    }

    @Override
    public void validate(Object target) {
        StudentGroup studentGroup = (StudentGroup) target;

        if (checkExistenceOfTheStudentGroup(studentGroup)) {
            checkExistenceOfTheDepartment(studentGroup);
            checkExistenceOfTheCurator(studentGroup);
            checkTeacherInDepartment(studentGroup);
            checkIfGroupLeaderInGroup(studentGroup);
        } else {
            checkExistenceOfTheDepartment(studentGroup);
            checkExistenceOfTheCurator(studentGroup);
            checkTeacherInDepartment(studentGroup);
            checkExistenceOfTheStudents(studentGroup);
        }
    }

    private boolean checkExistenceOfTheStudentGroup(StudentGroup studentGroup) {
        if (studentGroup.getId() != null) return true;
        else return false;
    }

    private void checkExistenceOfTheDepartment(StudentGroup studentGroup) {
        if (!findDepartment(studentGroup).isPresent())
            throw new IncorrectPropertyException("Department with name " + getDepartmentId(studentGroup) + " does not exist!");
    }

    private Optional<Department> findDepartment(StudentGroup studentGroup) {
        return departmentRepository.findById(getDepartmentId(studentGroup));
    }

    private Long getDepartmentId(StudentGroup studentGroup) {
        return getDepartmentFromGroup(studentGroup).getId();
    }

    private Department getDepartmentFromGroup(StudentGroup studentGroup) {
        return studentGroup.getDepartment();
    }

    private void checkExistenceOfTheStudents(StudentGroup studentGroup) {
        if (getStudents(studentGroup) != null)
            for (Student student : getStudents(studentGroup))
                checkStudentByUid(student);
    }

    private List<Student> getStudents(StudentGroup studentGroup) {
        return studentGroup.getStudents();
    }

    private void checkStudentByUid(Student student) {
        if (findStudent(student).isPresent())
            throw new IncorrectPropertyException("Person with uid " + getStudentId(student) + " already exists!");
    }

    private Optional<Person> findStudent(Student student) {
        return peopleRepository.findById(getStudentId(student));
    }

    private Long getStudentId(Student student) {
        return student.getUid();
    }

    private void checkExistenceOfTheCurator(StudentGroup studentGroup) {
        if (!findTeacher(studentGroup).isPresent())
            throw new IncorrectPropertyException(
                    "Teacher " + getTeacherSurname(studentGroup) +
                    " " + getTeacherName(studentGroup) +
                    " " + getTeacherPatronymic(studentGroup) +
                    " with uid " + getTeacherId(studentGroup) + " does not exist!"
            );
    }

    private Optional<Teacher> findTeacher(StudentGroup studentGroup) {
        return teacherRepository.findById(getTeacherId(studentGroup));
    }

    private Long getTeacherId(StudentGroup studentGroup) {
        return getTeacherFromGroup(studentGroup).getUid();
    }

    private Teacher getTeacherFromGroup(StudentGroup studentGroup) {
        return studentGroup.getCurator();
    }

    private String getTeacherSurname(StudentGroup studentGroup) {
        return getTeacherFromGroup(studentGroup).getSurname();
    }

    private String getTeacherName(StudentGroup studentGroup) {
        return getTeacherFromGroup(studentGroup).getName();
    }

    private String getTeacherPatronymic(StudentGroup studentGroup) {
        return getTeacherFromGroup(studentGroup).getPatronymic();
    }

    private void checkTeacherInDepartment(StudentGroup studentGroup) {
        if (teacherFromDepartment(findTeacher(studentGroup), findDepartment(studentGroup)))
            throw new IncorrectPropertyException(
                    "Teacher " + getTeacherSurname(studentGroup) +
                    " " + getTeacherName(studentGroup) +
                    " " + getTeacherPatronymic(studentGroup) +
                    " with uid " + getTeacherId(studentGroup) +
                    " is not listed at the department " + getDepartmentId(studentGroup) + "!"
            );
    }

    private boolean teacherFromDepartment(Optional<Teacher> testTeacher, Optional<Department> department) {
        if (!department.get().getTeachers().stream().anyMatch(teacher -> teacher.getUid().equals(testTeacher.get().getUid())))
            return true;
        else return false;
    }

    private void checkIfGroupLeaderInGroup(StudentGroup studentGroup) {
        if (studentNotFromGroup(findGroupLeader(studentGroup), findStudentGroup(studentGroup)))
            throw new IncorrectPropertyException(
                            "Student " + getLeaderSurname(studentGroup) +
                            " " + getLeaderName(studentGroup) +
                            " " + getLeaderPatronymic(studentGroup) +
                            " with uid " + getLeaderId(studentGroup) +
                            " is not listed in the student group " + getGroupName(studentGroup) + "!");
    }

    private Optional<StudentGroup> findStudentGroup(StudentGroup studentGroup) {
        return studentGroupRepository.findById(getGroupId(studentGroup));
    }

    private Long getGroupId(StudentGroup studentGroup) {
        return studentGroup.getId();
    }

    private String getGroupName(StudentGroup studentGroup) {
        return studentGroup.getName();
    }

    private boolean studentNotFromGroup(Optional<Student> testStudent, Optional<StudentGroup> studentGroup) {
        if (!studentGroup.get().getStudents().stream().anyMatch(student -> student.getUid().equals(testStudent.get().getUid())))
            return true;
        else return false;
    }

    private Optional<Student> findGroupLeader(StudentGroup studentGroup) {
        return studentRepository.getByUid(getLeaderId(studentGroup));
    }

    private Long getLeaderId(StudentGroup studentGroup) {
        return getLeader(studentGroup).getUid();
    }

    private Student getLeader(StudentGroup studentGroup) {
        return studentGroup.getGroupLeader();
    }

    private String getLeaderSurname(StudentGroup studentGroup) {
        return getLeader(studentGroup).getSurname();
    }

    private String getLeaderName(StudentGroup studentGroup) {
        return getLeader(studentGroup).getName();
    }

    private String getLeaderPatronymic(StudentGroup studentGroup) {
        return getLeader(studentGroup).getPatronymic();
    }
}

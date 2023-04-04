package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.exceptions.IncorrectPropertyException;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;
import ua.dgma.electronicDeansOffice.repositories.TeacherRepository;
import ua.dgma.electronicDeansOffice.utill.validators.data.StudentGroupValidationData;

import java.util.List;
import java.util.Optional;

@Component
public class StudentGroupValidator implements AbstractValidator {

    private final StudentGroupRepository studentGroupRepository;
    private final DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentGroupValidator(StudentGroupRepository studentGroupRepository,
                                 DepartmentRepository departmentRepository,
                                 TeacherRepository teacherRepository,
                                 StudentRepository studentRepository) {
        this.studentGroupRepository = studentGroupRepository;
        this.departmentRepository = departmentRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }
    @Override
    public void validate(Object target) {
        StudentGroup studentGroup = (StudentGroup) target;
        StudentGroupValidationData validationData = new StudentGroupValidationData(studentGroup, studentGroupRepository, departmentRepository, teacherRepository, studentRepository);

         if(checkExistenceOfTheStudentGroup(validationData)){
             checkExistenceOfTheDepartment(validationData);
             checkExistenceOfTheCurator(validationData);
             checkIfTheTeacherInDepartment(validationData);
             checkExistenceOfTheStudentsByUid(validationData);
         } else {
             checkExistenceOfTheDepartment(validationData);
             checkExistenceOfTheCurator(validationData);
             checkIfTheTeacherInDepartment(validationData);
             checkIfGroupLeaderInGroup(validationData);
         }
    }

    private boolean checkExistenceOfTheStudentGroup(StudentGroupValidationData data) {
        if(data.getStudentGroupRepository().getByName((data.getStudentGroup().getName())).isPresent()) return false; else return true;
    }

    private void checkExistenceOfTheDepartment(StudentGroupValidationData data) {
        if(!data.getDepartmentRepository().getByName(data.getStudentGroup().getDepartment().getName().toString()).isPresent())
            throw new IncorrectPropertyException("Department with name " + data.getStudentGroup().getDepartment().getName() + " does not exist!");
    }

    private void checkExistenceOfTheStudentsByUid(StudentGroupValidationData data) {
        List<Student> newStudents = data.getStudentGroup().getStudents();

        for (Student student : newStudents) {
            checkStudentsByUid(data, student);
        }
    }

    private void checkStudentsByUid(StudentGroupValidationData data, Student student) {
        if(data.getStudentRepository().getByUid(student.getUid()).isPresent())
            throw new IncorrectPropertyException("Student with uid " + student.getUid() + " already exists!");
    }

    private void checkExistenceOfTheCurator(StudentGroupValidationData data) {
        if(!data.getTeacherRepository().getByUid(data.getStudentGroup().getCurator().getUid().longValue()).isPresent())
            throw new IncorrectPropertyException("Teacher " + data.getStudentGroup().getCurator().getSurname() + " " + data.getStudentGroup().getCurator().getName() + " " + data.getStudentGroup().getCurator().getPatronymic() + " with uid " + data.getStudentGroup().getCurator().getUid() + " does not exist!");
    }

    private void checkIfTheTeacherInDepartment(StudentGroupValidationData data) {
        Optional<Teacher> curator = data.getTeacherRepository().getByUid(data.getStudentGroup().getCurator().getUid().longValue());
        Optional<Department> department = data.getDepartmentRepository().getByName(data.getStudentGroup().getDepartment().getName().toString());

        if(!department.get().getTeachers().stream().anyMatch(teacher -> teacher.getUid().equals(curator.get().getUid())))
            throw new IncorrectPropertyException("Teacher " + data.getStudentGroup().getCurator().getSurname() + " " + data.getStudentGroup().getCurator().getName() + " " + data.getStudentGroup().getCurator().getPatronymic() + " with uid " + data.getStudentGroup().getCurator().getUid() + " is not listed at the department " + data.getStudentGroup().getDepartment().getName() + "!");
    }

    private void checkIfGroupLeaderInGroup(StudentGroupValidationData data) {
        Optional<StudentGroup> existingStudentGroup = data.getStudentGroupRepository().getByName((data.getStudentGroup().getName().toString()));
        Optional<Student> groupLeader = data.getStudentRepository().getByUid(data.getStudentGroup().getGroupLeader().getUid().longValue());

        if(!existingStudentGroup.get().getStudents().stream().anyMatch(student -> student.getUid().equals(groupLeader.get().getUid())))
            throw new IncorrectPropertyException("Student " + data.getStudentGroup().getGroupLeader().getSurname() + " " + data.getStudentGroup().getGroupLeader().getName() + " " + data.getStudentGroup().getGroupLeader().getPatronymic() + " with uid " + data.getStudentGroup().getGroupLeader().getUid() + " is not listed in the student group " + data.getStudentGroup().getName() + "!");
    }

}

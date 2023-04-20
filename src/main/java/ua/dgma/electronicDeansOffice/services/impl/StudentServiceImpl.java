package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.models.Report;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.ReportRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentService;
import ua.dgma.electronicDeansOffice.services.specifications.StudentSpecifications;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.StudentValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceObjectWithSuchIDBeforeRegistrationOrUpdate;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceObjectWithSuchID;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl extends PeopleServiceImpl<Student> implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final ReportRepository reportRepository;
    private final StudentValidator studentValidator;
    private final StudentSpecifications specifications;
    private String className;

    @Autowired
    protected StudentServiceImpl(StudentRepository studentRepository,
                                 StudentValidator studentValidator,
                                 StudentGroupRepository studentGroupRepository,
                                 ReportRepository reportRepository,
                                 StudentSpecifications specifications) {
        super(studentRepository, specifications);
        this.studentRepository = studentRepository;
        this.studentValidator = studentValidator;
        this.studentGroupRepository = studentGroupRepository;
        this.reportRepository = reportRepository;
        this.specifications = specifications;
        this.className = Student.class.getSimpleName();
    }

    @Override
    protected Specification getSpec(FindAllData data) {
        return Specification.where(specifications.findStudentsByFacultyCriteria(data.getFacultyId()).and(specifications.getObjectByDeletedCriteria(data.getDeleted())));
    }

    @Override
    public void register(RegisterPersonData<Student> data) {
        checkExistenceObjectWithSuchIDBeforeRegistrationOrUpdate(new CheckExistsByIdData<>(className, getPersonUid(data), studentRepository));
        validateObject(new ValidationData<>(studentValidator, data.getNewPerson(), data.getBindingResult()));

        Student newStudent = data.getNewPerson();
        setStudentGroupForStudent(newStudent);

        savePerson(newStudent);
    }

    private void setStudentGroupForStudent(Student student) {
        student.setStudentGroup(getStudentGroup(student));
    }

    private StudentGroup getStudentGroup(Student student) {
        return studentGroupRepository.getByName(getStudentGroupName(student)).get();
    }

    private String getStudentGroupName(Student student) {
        return student.getStudentGroup().getName();
    }

    @Override
    public void update(UpdatePersonData<Student> data) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, data.getUid(), studentRepository));
        validateObject(new ValidationData<>(studentValidator, data.getUpdatedPerson(), data.getBindingResult()));

        Student updatedStudent = data.getUpdatedPerson();

        setIdInUpdatedPerson(updatedStudent, data);
        setStudentGroupForStudent(updatedStudent);

        savePerson(updatedStudent);
    }

    @Override
    public void delete(Long uid) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, uid, studentRepository));

        checkLeadersOfStudentGroups(uid);

        studentRepository.deleteByUid(uid);
    }

    private void checkLeadersOfStudentGroups(Long studentUid) {
        if (getGroupByLeader(studentUid).isPresent())
            removeLeaderFromGroup(studentUid);
    }

    private Optional<StudentGroup> getGroupByLeader(Long studentUid) {
        return studentGroupRepository.getByGroupLeader_Uid(studentUid);
    }

    private void removeLeaderFromGroup(Long studentUid) {
        getGroupByLeader(studentUid).get().setGroupLeader(null);
    }

    @Override
    public void softDelete(Long uid) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, uid, studentRepository));

        savePerson(markPersonAsDeleted(findByUid(uid)));
    }

    @Override
    public void softDeletePeople(List<Student> people) {
        for (Student student : people)
            softDelete(student.getUid());
    }

    private LocalDateTime date = LocalDateTime.now();

    @Override
    public Double getAvgAttendanceForStudent(Long studentUId) {
        Student student = findByUid(studentUId);
        StudentGroup group = findGroupByStudent(student);
        List<Report> reports = findReportsByGroup(group.getId());

        double countedReports = 0;

        for (Report report : reports)
            if (report.getStudentAttendance().get(student.getUid().longValue()))
                countedReports++;

        double avgAttendance = (countedReports / reports.size()) * 100;
        return avgAttendance;
    }

    private StudentGroup findGroupByStudent(Student student) {
        return studentGroupRepository.getByStudentsContaining(student).get();
    }

    private List<Report> findReportsByGroup(Long groupId) {
        return reportRepository.getByStudentGroup_IdAndCreatedBetween(groupId, date, LocalDateTime.now()).get();
    }
}

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
import ua.dgma.electronicDeansOffice.services.impl.data.student.DataForStudentAttendance;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentService;
import ua.dgma.electronicDeansOffice.services.specifications.StudentSpecifications;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.StudentValidator;

import java.time.LocalDateTime;
import java.util.*;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceObjectWithSuchID;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceObjectWithSuchIDBeforeRegistrationOrUpdate;

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

    @Override
    public Map<Long, Double> getAvgAttendanceForStudent(DataForStudentAttendance data) {
        Map<Long, Double> studentAttendance = new HashMap<>();
        Student student = findByUid(getStudentId(data));
        List<Report> reports = findReportsByGroup(data);
        double countedReports = 0;


        if (reports.size() != 0) {
            for (Report report : reports)
                if (report.getStudentAttendance().get(student.getUid()))
                    countedReports++;

            double avgAttendance = (countedReports / reports.size()) * 100;
            studentAttendance.put(getStudentId(data), avgAttendance);
        }
        else studentAttendance.put(getStudentId(data), 0.0);

        return studentAttendance;
    }

    private List<Report> findReportsByGroup(DataForStudentAttendance data) {
        if (getSearchFrom(data) == null)
            if (getSemester(data) != null)
                return reportRepository.getByStudentGroup_IdAndEventData_Semester(getGroupId(data), getSemester(data)).get();
            else
                return reportRepository.getByStudentGroup_Id(getGroupId(data)).get();
        else
            return reportRepository.getByStudentGroup_IdAndCreatedBetween(getGroupId(data), getSearchFrom(data), LocalDateTime.now()).get();
    }

    private LocalDateTime getSearchFrom(DataForStudentAttendance data) {
        return data.getSearchFrom();
    }

    private Long getStudentId(DataForStudentAttendance data) {
        return data.getStudentId();
    }

    private Integer getSemester(DataForStudentAttendance data) {
        return data.getSemester();
    }

    private Long getGroupId(DataForStudentAttendance data) {
        return findGroupByStudent(data).getId();
    }

    private StudentGroup findGroupByStudent(DataForStudentAttendance data) {
        return studentGroupRepository.getByStudentsContaining(getStudent(data)).get();
    }

    private Student getStudent(DataForStudentAttendance data) {
        return findByUid(getStudentId(data));
    }

    @Override
    public Map<Long, Double> getAvgAttendanceForStudentsOnFaculty(FindAllData data) {
        Map<Long, Double> facultyAttendance = new HashMap<>();

        for (Long studentId : getStudentsUid(data))
            facultyAttendance.putAll(getAvgAttendanceForStudent(new DataForStudentAttendance(studentId, getSemester(data), getSearchFrom(data))));

        return facultyAttendance;
    }

    private Set<Long> getStudentsUid(FindAllData data) {
        Set<Long> uids = new HashSet<>();

        for (Student student : findAllPeople(data)) {
            uids.add(student.getUid());
        }

        return uids;
    }

    private Integer getSemester(FindAllData data) {
        return data.getSemester();
    }

    private LocalDateTime getSearchFrom(FindAllData data) {
        return data.getSearchFrom();
    }
}

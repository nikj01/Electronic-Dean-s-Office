package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.RegisterStudentGroupData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.UpdateStudentGroupData;

import java.util.List;

public interface StudentGroupService {
    StudentGroup findOne(Long groupId);
    List<StudentGroup> findByName(String groupName);
    StudentGroup findByStudent(Student student);
    List<StudentGroup> findAll(FindAllData data);
    void register(RegisterStudentGroupData data);
    void update(UpdateStudentGroupData data);
    void delete(Long groupId);
    void softDelete(Long groupId);
    void softDeleteStudentGroups(List<StudentGroup> studentGroups);
}
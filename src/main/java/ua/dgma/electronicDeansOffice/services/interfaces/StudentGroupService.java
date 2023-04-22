package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.RegisterStudentGroupData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.UpdateStudentGroupData;

import java.util.List;
import java.util.Map;

public interface StudentGroupService {
    StudentGroup findOne(Long groupId);
    List<StudentGroup> findByName(String groupName);
    List<StudentGroup> findAll(FindAllData data);
    void register(RegisterStudentGroupData data);
    void update(UpdateStudentGroupData data);
    void delete(Long groupId);
    void softDelete(Long groupId);
    void softDeleteStudentGroups(List<StudentGroup> studentGroups);
    Map<Long, Double> getAvgAttendanceForGroup(Long groupId);
    Map<Long, Double> getAvgAttendanceForGroupsOnFaculty(FindAllData data);
}
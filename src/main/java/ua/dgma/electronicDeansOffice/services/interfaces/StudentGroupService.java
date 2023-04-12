package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.RegisterStudentGroupData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.UpdateStudentGroupData;

import java.util.List;

public interface StudentGroupService {
    List<StudentGroup> findByName(String name);
    List<StudentGroup> findAll(FindAllData data);
    void registerNew(RegisterStudentGroupData data);
    void update(UpdateStudentGroupData data);
    void delete(String name);
    void softDelete(String name);
    void softDeleteStudentGroups(List<StudentGroup> studentGroups);
}
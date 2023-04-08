package ua.dgma.electronicDeansOffice.services.interfaces;

import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.RegisterStudentGroupData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.UpdateStudentGroupData;

import java.util.List;

public interface StudentGroupService {
    StudentGroup findById(Long id);
    StudentGroup findByName(String name);
    List<StudentGroup> findAllStudentGroups(FindAllData data);
    void registerNew(RegisterStudentGroupData data);
    void updateByName(UpdateStudentGroupData data);
    void deleteByName(String name);
    void softDeleteByName(String name);

}
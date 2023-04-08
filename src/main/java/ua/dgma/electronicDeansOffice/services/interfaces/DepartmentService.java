package ua.dgma.electronicDeansOffice.services.interfaces;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.department.RegisterDepartmentData;
import ua.dgma.electronicDeansOffice.services.impl.data.department.UpdateDepartmentData;
import ua.dgma.electronicDeansOffice.services.impl.data.studentGroup.RegisterStudentGroupData;

import java.util.List;
import java.util.Set;

public interface DepartmentService {
    List<Department> findByName(String name);
    List<Department> findAllDepartments(FindAllData data);
    void registerNew(RegisterDepartmentData data);
    void updateByName(UpdateDepartmentData data);
    void deleteByName(String name);
    void softDeleteByName(String name);
}

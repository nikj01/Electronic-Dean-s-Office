package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.department.RegisterDepartmentData;
import ua.dgma.electronicDeansOffice.services.impl.data.department.UpdateDepartmentData;

import java.util.List;

public interface DepartmentService {
    Department findOne(Long departmentId);
    List<Department> findByName(String departmentName);
    List<Department> findAllDepartments(FindAllData data);
    void register(RegisterDepartmentData data);
    void update(UpdateDepartmentData data);
    void delete(Long departmentId);
    void softDelete(Long departmentId);
    void softDeleteDepartments(List<Department> departments);
}

package ua.dgma.electronicDeansOffice.services.interfaces;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Department;

import java.util.List;
import java.util.Set;

public interface DepartmentService {

    Department findById(Long id);
    Department findByName(String name);
    List<Department> findAllDepartmentsByFacultyName(String facultyName);
    List<Department> findAllWithPaginationOrWithout(Integer page, Integer departmentsPerPage, Boolean isDeleted);
    void registerNew(Department department, BindingResult bindingResult);
    void updateByName(String name, Department updatedDepartment, BindingResult bindingResult);
    void deleteByName(String name);
    void softDeleteByName(String name);
}

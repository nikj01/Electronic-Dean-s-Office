package ua.dgma.electronicDeansOffice.services.interfaces;

import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Department;

import java.util.List;
import java.util.Set;

public interface DepartmentService {

    Department findByName(String name);

    List<Department> findAllWithPaginationOrWithout(Integer page, Integer peoplePerPage);

    void registerNew(Department department, BindingResult bindingResult);

    void updateByName(String name, Department updatedDepartment, BindingResult bindingResult);

    void deleteByName(String name);

    void validateDepartment(Department department, BindingResult bindingResult);

    void checkExistsWithSuchName(String name);
}

package ua.dgma.electronicDeansOffice.services.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Faculty;

import java.util.List;

public interface FacultyService {
    Faculty findById(Long id);
    Faculty findByName(String name);
    List<Faculty> findAllWithPaginationOrWithout(Integer page, Integer facultiesPerPage, Boolean isDeleted);
    void registerNew(Faculty faculty, BindingResult bindingResult);
    void updateByName(String name, Faculty updatedFaculty, BindingResult bindingResult);
    void deleteByName(String name);
    void softDeleteByName(String name);

}

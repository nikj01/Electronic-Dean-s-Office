package ua.dgma.electronicDeansOffice.services.interfaces;

import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Faculty;

import java.util.List;

public interface FacultyService {

    Faculty findByName(String name);

    List<Faculty> findAllWithPaginationOrWithout(Integer page, Integer peoplePerPage);

    void registerNew(Faculty faculty, BindingResult bindingResult);

    void updateByName(String name, Faculty updatedFaculty, BindingResult bindingResult);

    void deleteByName(String name);

    void validateFaculty(Faculty faculty, BindingResult bindingResult);

    void checkExistsWithSuchName(String name);

}

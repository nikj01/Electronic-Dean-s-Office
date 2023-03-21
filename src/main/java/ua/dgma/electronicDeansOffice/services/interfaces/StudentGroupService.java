package ua.dgma.electronicDeansOffice.services.interfaces;

import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

import java.util.List;

public interface StudentGroupService {

    StudentGroup findByName(String name);

    List<StudentGroup> findAllWithPaginationOrWithout(Integer page, Integer peoplePerPage);

    void registerNew(StudentGroup studentGroup, BindingResult bindingResult);

    void updateByName(String name, StudentGroup updatedStudentGroup, BindingResult bindingResult);

    void deleteByName(String name);

    void validateStudentGroup(StudentGroup studentGroup, BindingResult bindingResult);

    void checkExistsWithSuchName(String name);

}

package ua.dgma.electronicDeansOffice.services.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.faculty.RegisterFacultyData;
import ua.dgma.electronicDeansOffice.services.impl.data.faculty.UpdateFacultyData;

import java.util.List;

public interface FacultyService {
    Faculty findByName(String name);
    List<Faculty> findAllFaculties(FindAllData data);
    void registerNew(RegisterFacultyData data);
    void updateByName(UpdateFacultyData data);
    void deleteByName(String name);
    void softDeleteByName(String name);

}

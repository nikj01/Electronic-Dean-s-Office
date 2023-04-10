package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.faculty.RegisterFacultyData;
import ua.dgma.electronicDeansOffice.services.impl.data.faculty.UpdateFacultyData;

import java.util.List;

public interface FacultyService {
    List<Faculty> findByName(String name);
    List<Faculty> findAllFaculties(FindAllData data);
    void registerNew(RegisterFacultyData data);
    void updateFaculty(UpdateFacultyData data);
    void deleteFaculty(String name);
    void softDeleteFaculty(String name);

}

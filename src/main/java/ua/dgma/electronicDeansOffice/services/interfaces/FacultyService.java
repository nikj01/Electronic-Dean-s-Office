package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.faculty.RegisterFacultyData;
import ua.dgma.electronicDeansOffice.services.impl.data.faculty.UpdateFacultyData;

import java.util.List;

public interface FacultyService {
    Faculty findOne(Long facultyId);
    List<Faculty> findByName(String facultyName);
    List<Faculty> findAll(FindAllData data);
    void register(RegisterFacultyData data);
    void update(UpdateFacultyData data);
    void delete(Long facultyId);
    void softDelete(Long facultyId);
}

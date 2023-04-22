package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.student.DataForStudentAttendance;

import java.util.Map;

public interface StudentService extends PeopleService<Student>{
    Map<Long, Double> getAvgAttendanceForStudent(DataForStudentAttendance data);
    Map<Long, Double> getAvgAttendanceForStudentsOnFaculty(FindAllData data);
}

package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.student.DataForStudentStatistics;

import java.util.Map;

public interface StudentService extends PeopleService<Student>{
    Map<Long, Double> getAvgAttendanceForStudent(DataForStudentStatistics data);
    Map<Long, Double> getAvgAttendanceForStudentsOnFaculty(FindAllData data);

}

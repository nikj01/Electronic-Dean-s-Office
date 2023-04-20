package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.models.Student;

public interface StudentService extends PeopleService<Student>{
    Double getAvgAttendanceForStudent(Long studentUId);
}

package ua.dgma.electronicDeansOffice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentsResponse {

    private List<StudentDTO> students;

    public StudentsResponse(List<StudentDTO> students) {
        this.students = students;
    }

}

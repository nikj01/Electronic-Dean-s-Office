package ua.dgma.electronicDeansOffice.mapstruct.dtos.student;

import lombok.*;

import java.util.List;

@Data
public class StudentsGetDTO {

    private List<StudentGetDTO> students;
}

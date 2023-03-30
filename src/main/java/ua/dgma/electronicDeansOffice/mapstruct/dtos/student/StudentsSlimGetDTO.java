package ua.dgma.electronicDeansOffice.mapstruct.dtos.student;

import lombok.*;

import java.util.List;

@Data
public class StudentsSlimGetDTO {

    private List<StudentSlimGetDTO> students;
}

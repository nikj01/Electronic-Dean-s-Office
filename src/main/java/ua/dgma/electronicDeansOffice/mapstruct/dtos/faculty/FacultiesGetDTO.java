package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import lombok.*;

import java.util.List;

@Data
public class FacultiesGetDTO {

    private List<FacultyGetDTO> faculties;
}

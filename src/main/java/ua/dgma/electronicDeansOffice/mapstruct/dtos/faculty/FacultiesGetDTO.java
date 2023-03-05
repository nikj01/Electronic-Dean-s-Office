package ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class FacultiesGetDTO {

    @NonNull
    private List<FacultyGetDTO> faculties;
}

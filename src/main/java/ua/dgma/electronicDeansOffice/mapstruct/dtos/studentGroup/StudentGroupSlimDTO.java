package ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StudentGroupSlimDTO {

    @NotBlank
    private String groupName;
}

package ua.dgma.electronicDeansOffice.mapstruct.dtos.student;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;

@Data
//@JsonIdentityInfo(   generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "uid")
//@JsonIdentityReference(alwaysAsId = true)
public class StudentSlimGetDTO {
    private Long uid;
    private String surname;
    private String name;
    private String patronymic;
    private StudentGroupSlimGetDTO studentGroup;
    private boolean deleted;
}

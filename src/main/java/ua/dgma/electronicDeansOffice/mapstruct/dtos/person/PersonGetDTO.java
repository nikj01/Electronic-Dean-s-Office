package ua.dgma.electronicDeansOffice.mapstruct.dtos.person;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.models.PersonRole;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class PersonGetDTO extends PersonSlimGetDTO {

    private Long uid;
    private PersonRole role;
    private Date dateOfBirth;
    private String email;
    private String password;
}

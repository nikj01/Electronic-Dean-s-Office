package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.*;
import ua.dgma.electronicDeansOffice.models.Student;

import java.util.List;
@Mapper(componentModel = "spring", uses = {StudentGroupMapper.class, PersonMapper.class}, injectionStrategy = InjectionStrategy.FIELD)
public interface StudentMapper {

    Student toStudent(StudentPostDTO studentPost);
    StudentGetDTO toStudentGetDTO(Student student);
    StudentSlimGetDTO toStudentSlimGetDTO(Student student);

}

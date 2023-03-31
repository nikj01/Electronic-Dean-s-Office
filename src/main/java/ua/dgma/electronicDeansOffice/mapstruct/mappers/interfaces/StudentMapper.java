package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.*;
import ua.dgma.electronicDeansOffice.models.Student;

import java.util.List;
@Mapper(componentModel = "spring", uses = {StudentGroupMapper.class, PersonMapper.class}, injectionStrategy = InjectionStrategy.FIELD)
public interface StudentMapper {
    @Named(value = "postStudent")
    Student toStudent(StudentPostDTO studentPost);
    @Named(value = "patchStudent")
    Student toStudent(StudentPatchDTO studentPatch);
    StudentGetDTO toStudentGetDTO(Student student);
    StudentSlimGetDTO toStudentSlimGetDTO(Student student);

}

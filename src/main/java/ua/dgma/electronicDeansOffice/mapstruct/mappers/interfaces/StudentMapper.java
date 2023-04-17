package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.Student;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface StudentMapper {
    @Named("postStudent")
    Student toStudent(StudentPostDTO studentPostDTO);
    @Named("patchStudent")
    Student toStudent(StudentPatchDTO studentPatchDTO);
    @Named("student")
    StudentGetDTO toStudentGetDTO(Student student);
    @Named("slimStudent")
    StudentSlimGetDTO toStudentSlimGetDTO(Student student);

}

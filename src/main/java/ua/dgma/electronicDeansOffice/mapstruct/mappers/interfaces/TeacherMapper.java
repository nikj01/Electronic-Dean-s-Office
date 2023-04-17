package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.Teacher;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TeacherMapper {
    @Named("postTeacher")
    Teacher toTeacher(TeacherPostDTO teacherPostDTO);
    @Named("patchTeacher")
    Teacher toTeacher(TeacherPatchDTO teacherPatchDTO);
    @Named("teacher")
    TeacherGetDTO toTeacherGetDTO(Teacher teacher);
    @Named("slimTeacher")
    TeacherSlimGetDTO toTeacherSlimGetDTO(Teacher teacher);
}

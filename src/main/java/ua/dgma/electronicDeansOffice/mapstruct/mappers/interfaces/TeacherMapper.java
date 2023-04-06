package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.StudentGroupListMapper;
import ua.dgma.electronicDeansOffice.models.Teacher;

@Mapper(componentModel = "spring",
        uses = {DepartmentMapper.class, StudentGroupListMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TeacherMapper {
    @Named(value = "postTeacher")
    Teacher toTeacher(TeacherPostDTO teacherPost);
    @Named(value = "patchTeacher")
    Teacher toTeacher(TeacherPatchDTO teacherPatch);
    @Named(value = "teacher")
    TeacherGetDTO toTeacherGetDTO(Teacher teacher);
    @Named(value = "slimTeacher")
    TeacherSlimGetDTO toTeacherSlimGetDTO(Teacher teacher);
}

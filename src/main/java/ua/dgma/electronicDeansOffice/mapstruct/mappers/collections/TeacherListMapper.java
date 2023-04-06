package ua.dgma.electronicDeansOffice.mapstruct.mappers.collections;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teacher.TeacherSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.TeacherMapper;
import ua.dgma.electronicDeansOffice.models.Teacher;

import java.util.List;

@Mapper(componentModel = "spring", uses = TeacherMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TeacherListMapper {

    @IterableMapping(qualifiedByName = "teacher")
    List<TeacherGetDTO> toTeachersGetDTO(List<Teacher> teachers);
    @IterableMapping(qualifiedByName = "slimTeacher")
    List<TeacherSlimGetDTO> toTeachersSlimGetDTO(List<Teacher> teachers);
}

package ua.dgma.electronicDeansOffice.mapstruct.mappers.collections;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.student.StudentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.PersonMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.StudentGroupMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.StudentMapper;
import ua.dgma.electronicDeansOffice.models.Student;

import java.util.List;

@Mapper(componentModel = "spring", uses = StudentMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StudentListMapper {

    List<StudentGetDTO> toStudentsGetDTO(List<Student> students);
    List<StudentSlimGetDTO> toStudentsSlimGetDTO(List<Student> students);
}

package ua.dgma.electronicDeansOffice.mapstruct.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.StudentGroupMapper;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

import java.util.List;

@Component
public class StudentGroupMapperImpl implements StudentGroupMapper {

    private final ModelMapper mapper;

    @Autowired
    public StudentGroupMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public StudentGroup convertToStudentGroup(StudentGroupPostDTO studentGroup) {
        return null;
    }

    @Override
    public StudentGroupGetDTO convertToStudentGroupGetDTO(StudentGroup studentGroup) {
        return null;
    }

    @Override
    public StudentGroupSlimGetDTO convertToStudentGroupSlimGetDTO(StudentGroup studentGroup) {
        return null;
    }

    @Override
    public StudentGroupsGetDTO convertToStudentGroupsGetDTO(List<StudentGroup> studentGroups) {
        return null;
    }

    @Override
    public StudentGroupsSlimGetDTO convertToStudentGroupsSlimGetDTO(List<StudentGroup> studentGroups) {
        return null;
    }
}

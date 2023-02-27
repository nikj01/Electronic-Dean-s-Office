package ua.dgma.electronicDeansOffice.mapstruct.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.studentGroup.StudentGroupSlimPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.StudentGroupMapper;
import ua.dgma.electronicDeansOffice.models.StudentGroup;

@Component
public class StudentGroupMapperImpl implements StudentGroupMapper {

    private final ModelMapper mapper;

    @Autowired
    public StudentGroupMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public StudentGroupSlimGetDTO convertToStudentGroupSlim(StudentGroup studentGroup) {
        return mapper.map(studentGroup, StudentGroupSlimGetDTO.class);
    }

}

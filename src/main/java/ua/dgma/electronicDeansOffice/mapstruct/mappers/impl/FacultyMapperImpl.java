package ua.dgma.electronicDeansOffice.mapstruct.mappers.impl;

import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.FacultyMapper;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.services.impl.DepartmentServiceImpl;
import ua.dgma.electronicDeansOffice.utill.ObjectMapperUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FacultyMapperImpl implements FacultyMapper {

    private final ModelMapper mapper;
    private final DepartmentMapperImpl departmentMapper;
    private final DepartmentServiceImpl departmentService;

    @Autowired
    public FacultyMapperImpl(ModelMapper mapper, DepartmentMapperImpl departmentMapper, DepartmentServiceImpl departmentService) {
        this.mapper = mapper;
        this.departmentMapper = departmentMapper;
        this.departmentService = departmentService;
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE)
                .setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
//        mapper.addMappings(new PropertyMap<Department, DepartmentSlimGetDTO>() {
//                               @Override
//                               protected void configure() {
//                                   Converter<List<Department>, List<DepartmentSlimGetDTO>> departmentConverter = new Converter<List<Department>, List<DepartmentSlimGetDTO>>() {
//                                       @Override
//                                       public List<DepartmentSlimGetDTO> convert(MappingContext<List<Department>, List<DepartmentSlimGetDTO>> mappingContext) {
//                                           List<DepartmentSlimGetDTO> result = new ArrayList<>();
//                                           for (Department department : mappingContext.getSource())
//                                               result.add(departmentMapper.convertToDepartmentSlimGetDTO(department));
//                                           return result;
//                                       }
//                                   };
//                               }
//                           });
    }

    @Override
    public Faculty convertToFaculty(FacultyPostDTO facultyPost) {
        return mapper.map(facultyPost, Faculty.class);
    }

//    @Override
//    public FacultyGetDTO convertToFacultyGetDTO(Faculty faculty) {
//        return mapper.map(faculty, FacultyGetDTO.class) ;
//    }
//
    @Override
    public FacultyGetDTO convertToFacultyGetDTO(Faculty faculty) {
        return mapper.map(faculty, FacultyGetDTO.class) ;
    }
//
//    @Override
//    public FacultyGetDTO convertToFacultyGetDTO1(Faculty faculty) {
//        List<DepartmentSlimGetDTO> departments = ObjectMapperUtils.mapAll(departmentService.findAllDepartmentsByFacultyName(faculty.getName()), DepartmentSlimGetDTO.class);
//        return mapper.map(faculty, FacultyGetDTO.class).setDepartments(departments);
//    }

    @Override
    public FacultySlimGetDTO convertToFacultySlimGetDTO(Faculty faculty) {
        return mapper.map(faculty, FacultySlimGetDTO.class);
    }

    @Override
    public FacultiesGetDTO convertToFacultiesGetDTO(List<Faculty> faculties) {
        return new FacultiesGetDTO(faculties.stream()
                                            .map(this::convertToFacultyGetDTO)
                                            .sorted()
                                            .collect(Collectors.toList()));
    }

    @Override
    public FacultiesSlimGetDTO convertToFacultiesSlimGetDTO(List<Faculty> faculties) {
        return new FacultiesSlimGetDTO(faculties.stream()
                                                .map(this::convertToFacultySlimGetDTO)
                                                .sorted()
                                                .collect(Collectors.toList()));
    }
}

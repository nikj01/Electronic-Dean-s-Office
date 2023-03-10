package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.exceptions.CustomException;
import ua.dgma.electronicDeansOffice.exceptions.ErrorResponse;
import ua.dgma.electronicDeansOffice.exceptions.people.NotFoundException;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.department.DepartmentsSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.impl.DepartmentMapperImpl;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.impl.FacultyMapperImpl;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.services.impl.DepartmentServiceImpl;
import ua.dgma.electronicDeansOffice.services.impl.FacultyServiceImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyServiceImpl facultyService;
    private final DepartmentServiceImpl departmentService;
    private final FacultyMapperImpl facultyMapper;
    private final DepartmentMapperImpl departmentMapper;

    @Autowired
    public FacultyController(FacultyServiceImpl facultyService,
                             DepartmentServiceImpl departmentService, FacultyMapperImpl facultyMapper, DepartmentMapperImpl departmentMapper) {
        this.facultyService = facultyService;
        this.departmentService = departmentService;
        this.facultyMapper = facultyMapper;
        this.departmentMapper = departmentMapper;
    }

    @GetMapping("/findByName")
    @ResponseStatus(HttpStatus.FOUND)
    public FacultyGetDTO findFacultyByName(@RequestParam("name") String name) {

        return facultyMapper.convertToFacultyGetDTO(facultyService.findByName(name));
    }

    @GetMapping("/slim/findByName")
    @ResponseStatus(HttpStatus.FOUND)
    public FacultySlimGetDTO findSlimFacultyByName(@RequestParam("name") String name) {
        return facultyMapper.convertToFacultySlimGetDTO(facultyService.findByName(name));
    }

    @GetMapping()
    public FacultiesGetDTO findAllFaculties(@RequestParam(value = "page", required = false) Integer page,
                                            @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
        return facultyMapper.convertToFacultiesGetDTO(facultyService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @GetMapping("/slim")
    public FacultiesSlimGetDTO findAllSlimFaculties(@RequestParam(value = "page", required = false) Integer page,
                                                    @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
        return facultyMapper.convertToFacultiesSlimGetDTO(facultyService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @PostMapping("/register")
    public void registerNewFaculty(@RequestBody @Valid FacultyPostDTO newPostFaculty,
                                                       BindingResult bindingResult) {
        Faculty newFaculty = facultyMapper.convertToFaculty(newPostFaculty);

        facultyService.registerNew(newFaculty, bindingResult);
    }

    @PatchMapping("/update")
    public void updateFaculty(@RequestParam("name") String name,
                              @RequestBody @Valid   FacultyPostDTO updatedPostFaculty,
                                                    BindingResult bindingResult) {
        Faculty updatedFaculty = facultyMapper.convertToFaculty(updatedPostFaculty);

        facultyService.updateByName(name, updatedFaculty, bindingResult);
    }

    @DeleteMapping("/delete")
    public void deleteFaculty(@RequestParam("name") String name) {
        facultyService.deleteByName(name);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(CustomException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}

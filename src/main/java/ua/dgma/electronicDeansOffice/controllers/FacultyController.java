package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.exceptions.CustomException;
import ua.dgma.electronicDeansOffice.exceptions.ErrorResponse;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.FacultyListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.FacultyMapper;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.services.interfaces.FacultyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;
    private final FacultyMapper facultyMapper;
    private final FacultyListMapper facultyListMapper;

    @Autowired
    public FacultyController(FacultyService facultyService,
                             FacultyMapper facultyMapper,
                             FacultyListMapper facultyListMapper) {
        this.facultyService = facultyService;
        this.facultyListMapper = facultyListMapper;
        this.facultyMapper = facultyMapper;
    }

    @GetMapping("/findByName")
    @ResponseStatus(HttpStatus.FOUND)
    public FacultyGetDTO findFacultyByName(@RequestParam("name") String name) {
        return facultyMapper.toFacultyGetDTO(facultyService.findByName(name));
    }


    @GetMapping("/slim/findByName")
    @ResponseStatus(HttpStatus.FOUND)
    public FacultySlimGetDTO findSlimFacultyByName(@RequestParam("name") String name) {
        return facultyMapper.toFacultySlimGetDTO(facultyService.findByName(name));
    }

    @GetMapping()
    public List<FacultyGetDTO> findAllFaculties(@RequestParam(value = "page", required = false) Integer page,
                                                @RequestParam(value = "facultiesPerPage", required = false) Integer facultiesPerPage,
                                                @RequestParam(value = "isDeleted", required = false, defaultValue = "false") Boolean isDeleted) {
        return facultyListMapper.toFacultiesGetDTO(facultyService.findAllWithPaginationOrWithout(page, facultiesPerPage, isDeleted));
    }

    @GetMapping("/slim")
    public List<FacultySlimGetDTO> findAllSlimFaculties(@RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "facultiesPerPage", required = false) Integer facultiesPerPage,
                                                        @RequestParam(value = "isDeleted", required = false, defaultValue = "false") Boolean isDeleted) {
        return facultyListMapper.toFacultiesSlimGetDTO(facultyService.findAllWithPaginationOrWithout(page, facultiesPerPage, isDeleted));
    }

    @PostMapping("/register")
    public void registerNewFaculty(@RequestBody @Valid FacultyPostDTO newPostFaculty,
                                                       BindingResult bindingResult) {
        Faculty newFaculty = facultyMapper.toFaculty(newPostFaculty);

        facultyService.registerNew(newFaculty, bindingResult);
    }

    /*
     * THIS METHOD WILL BE REMOVE
     * */
    @PatchMapping("/update")
    public void updateFaculty(@RequestParam("name") String name,
                              @RequestBody @Valid   FacultyPostDTO updatedPostFaculty,
                                                    BindingResult bindingResult) {
        Faculty updatedFaculty = facultyMapper.toFaculty(updatedPostFaculty);

        facultyService.updateByName(name, updatedFaculty, bindingResult);
    }

    @DeleteMapping("/delete")
    public void deleteFaculty(@RequestParam("name") String name) {
        facultyService.deleteByName(name);
    }

    @DeleteMapping("/soft/delete")
    public void softDeleteFaculty(@RequestParam("name") String name) {
        facultyService.softDeleteByName(name);
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

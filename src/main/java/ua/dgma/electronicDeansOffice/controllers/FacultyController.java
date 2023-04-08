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
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.faculty.RegisterFacultyData;
import ua.dgma.electronicDeansOffice.services.impl.data.faculty.UpdateFacultyData;
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
    public List<FacultySlimGetDTO> findFacultyByName(@RequestParam("name") String name) {
        return facultyListMapper.toFacultiesSlimGetDTO(facultyService.findByName(name));
    }

    @GetMapping("/slim/findByName")
    @ResponseStatus(HttpStatus.FOUND)
    public List<FacultySlimGetDTO> findSlimFacultyByName(@RequestParam("name") String name) {
//        return facultyMapper.toFacultySlimGetDTO(facultyService.findByName(name));
        return facultyListMapper.toFacultiesSlimGetDTO(facultyService.findByName(name));
    }

    @GetMapping("")
    public List<FacultySlimGetDTO> findAllSlimFaculties(@RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "facultiesPerPage", required = false) Integer facultiesPerPage,
                                                        @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted) {
        return facultyListMapper.toFacultiesSlimGetDTO(facultyService.findAllFaculties(new FindAllData(page, facultiesPerPage, deleted)));
    }

    @PostMapping("/register")
    public void registerNewFaculty(@RequestBody @Valid FacultyPostDTO newPostFaculty,
                                                       BindingResult bindingResult) {
        Faculty newFaculty = facultyMapper.toFaculty(newPostFaculty);

        facultyService.registerNew(new RegisterFacultyData(newFaculty, bindingResult));
    }

    @PatchMapping("/update")
    public void updateFaculty(@RequestParam("name") String name,
                              @RequestBody @Valid   FacultyPatchDTO facultyPatchDTO,
                                                    BindingResult bindingResult) {
        Faculty updatedFaculty = facultyMapper.toFaculty(facultyPatchDTO);

        facultyService.updateByName(new UpdateFacultyData(name, updatedFaculty, bindingResult));
    }

    @DeleteMapping("/delete")
    public void deleteFaculty(@RequestParam("name") String name) {
        facultyService.deleteByName(name);
    }

    @DeleteMapping("/soft/delete")
    public void softDeleteFaculty(@RequestParam("name") String name) {
        facultyService.softDeleteByName(name);
    }

}

package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultyGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultyPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultyPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.faculty.FacultySlimGetDTO;
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

    @GetMapping("/findById")
    @ResponseStatus(HttpStatus.FOUND)
    public FacultyGetDTO findFacultyById(@RequestParam("id") Long id) {
        return facultyMapper.toFacultyGetDTO(facultyService.findOne(id));
    }

    @GetMapping("/findByName")
    @ResponseStatus(HttpStatus.FOUND)
    public List<FacultySlimGetDTO> findFacultyByName(@RequestParam("name") String name) {
        return facultyListMapper.toFacultiesSlimGetDTO(facultyService.findByName(name));
    }

    @GetMapping("/slim/findByName")
    @ResponseStatus(HttpStatus.FOUND)
    public List<FacultySlimGetDTO> findSlimFacultyByName(@RequestParam("name") String name) {
        return facultyListMapper.toFacultiesSlimGetDTO(facultyService.findByName(name));
    }

    @GetMapping("")
    public List<FacultySlimGetDTO> findAllSlimFaculties(@RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "facultiesPerPage", required = false) Integer facultiesPerPage,
                                                        @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted) {
        return facultyListMapper.toFacultiesSlimGetDTO(facultyService.findAll(new FindAllData(page, facultiesPerPage, deleted)));
    }

    @PostMapping("/register")
    public void registerNewFaculty(@RequestBody @Valid FacultyPostDTO newPostFaculty,
                                                       BindingResult bindingResult) {
        Faculty newFaculty = facultyMapper.toFaculty(newPostFaculty);

        facultyService.register(new RegisterFacultyData(newFaculty, bindingResult));
    }

    @PatchMapping("/update")
    public void updateFaculty(@RequestParam("id") Long facultyId,
                              @RequestBody @Valid FacultyPatchDTO facultyPatchDTO,
                                                  BindingResult bindingResult) {
        Faculty updatedFaculty = facultyMapper.toFaculty(facultyPatchDTO);

        facultyService.update(new UpdateFacultyData(facultyId, updatedFaculty, bindingResult));
    }

    @DeleteMapping("/delete")
    public void deleteFaculty(@RequestParam("id") Long facultyId) {
        facultyService.delete(facultyId);
    }

    @DeleteMapping("/soft/delete")
    public void softDeleteFaculty(@RequestParam("id") Long facultyId) {
        facultyService.softDelete(facultyId);
    }

}

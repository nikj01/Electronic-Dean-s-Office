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
import ua.dgma.electronicDeansOffice.services.interfaces.ReportsAnalyzerForFaculty;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static ua.dgma.electronicDeansOffice.utill.ConvertData.convertData;

@RestController
@RequestMapping("/faculties")
public class FacultyController {
    private final FacultyService facultyService;
    private final ReportsAnalyzerForFaculty reportsAnalyzer;
    private final FacultyMapper facultyMapper;
    private final FacultyListMapper facultyListMapper;

    @Autowired
    public FacultyController(FacultyService facultyService,
                             ReportsAnalyzerForFaculty reportsAnalyzer,
                             FacultyMapper facultyMapper,
                             FacultyListMapper facultyListMapper) {
        this.facultyService = facultyService;
        this.reportsAnalyzer = reportsAnalyzer;
        this.facultyListMapper = facultyListMapper;
        this.facultyMapper = facultyMapper;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public FacultyGetDTO findFacultyById(@PathVariable("id") Long id) {
        return facultyMapper.toFacultyGetDTO(facultyService.findOne(id));
    }

    @GetMapping("names/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    public List<FacultySlimGetDTO> findSlimFacultyByName(@PathVariable("name") String name) {
        return facultyListMapper.toFacultiesSlimGetDTO(facultyService.findByName(name));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.FOUND)
    public List<FacultySlimGetDTO> findAllSlimFaculties(@RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "facultiesPerPage", required = false) Integer facultiesPerPage,
                                                        @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted) {
        return facultyListMapper.toFacultiesSlimGetDTO(facultyService.findAll(new FindAllData(page, facultiesPerPage, deleted)));
    }

    @GetMapping("{id}/attendance")
    @ResponseStatus(HttpStatus.FOUND)
    public Map<Long, Double> showStudentsAvgAttendanceOnFaculty(@PathVariable(value = "faculty", required = false) Long facultyId,
                                                                @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                                @RequestParam(value = "from", required = false) String searchFrom,
                                                                @RequestParam(value = "to", required = false) String searchTo) {
        return reportsAnalyzer.getAvgAttendanceForStudentsOnFaculty(new FindAllData(facultyId, convertData(searchFrom), convertData(searchTo), deleted));
    }


    @GetMapping("{id}/avgGrades")
    @ResponseStatus(HttpStatus.FOUND)
    public Map<Long, Double> showStudentsAvgGradesOnFaculty(@PathVariable(value = "faculty", required = false) Long facultyId,
                                                            @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                            @RequestParam(value = "from", required = false) String searchFrom,
                                                            @RequestParam(value = "to", required = false) String searchTo) {
        return reportsAnalyzer.getAvgGradeForStudentsOnFaculty(new FindAllData(facultyId, convertData(searchFrom), convertData(searchTo), deleted));
    }

    @GetMapping("{id}/groupsAttendance")
    @ResponseStatus(HttpStatus.FOUND)
    public Map<Long, Double> showGroupsAvgAttendanceOnFaculty(@PathVariable(value = "faculty", required = false) Long facultyId,
                                                              @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                              @RequestParam(value = "from", required = false) String searchFrom,
                                                              @RequestParam(value = "to", required = false) String searchTo) {
        return reportsAnalyzer.getAvgAttendanceForGroupsOnFaculty(new FindAllData(facultyId, convertData(searchFrom), convertData(searchTo), deleted));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewFaculty(@RequestBody @Valid FacultyPostDTO newPostFaculty,
                                                       BindingResult bindingResult) {
        Faculty newFaculty = facultyMapper.toFaculty(newPostFaculty);

        facultyService.register(new RegisterFacultyData(newFaculty, bindingResult));
    }

    @PatchMapping("{id}/update")
    public void updateFaculty(@PathVariable("id") Long facultyId,
                              @RequestBody @Valid FacultyPatchDTO facultyPatchDTO,
                                                  BindingResult bindingResult) {
        Faculty updatedFaculty = facultyMapper.toFaculty(facultyPatchDTO);

        facultyService.update(new UpdateFacultyData(facultyId, updatedFaculty, bindingResult));
    }

    @DeleteMapping("{id}/delete")
    public void deleteFaculty(@PathVariable("id") Long facultyId) {
        facultyService.delete(facultyId);
    }

    @DeleteMapping("{id}/softDelete")
    public void softDeleteFaculty(@PathVariable("id") Long facultyId) {
        facultyService.softDelete(facultyId);
    }

}

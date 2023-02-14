package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.services.impl.FacultyServiceImpl;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyServiceImpl facultyService;

    @Autowired
    public FacultyController(FacultyServiceImpl facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{name}")
    public Faculty findFaculty(@PathVariable("name") String name){
        Faculty foundFaculty = facultyService.findOne(name);
        return foundFaculty;
    }

    @GetMapping("/v1/{name}")
    public String findFacultyName(@PathVariable("name") String name){
        String facultyName = facultyService.getName((String) name);
        return facultyName;
    }
}

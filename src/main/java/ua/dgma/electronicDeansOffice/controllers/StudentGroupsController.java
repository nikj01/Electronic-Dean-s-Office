package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.StudentListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.StudentMapper;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;

@RestController
@RequestMapping("/studentGroups")
public class StudentGroupsController {

    private final StudentGroupService studentGroupService;
    private final StudentMapper studentMapper;
    private final StudentListMapper studentListMapper;

    @Autowired
    public StudentGroupsController(StudentGroupService studentGroupService,
                                   StudentMapper studentMapper,
                                   StudentListMapper studentListMapper) {
        this.studentGroupService = studentGroupService;
        this.studentMapper = studentMapper;
        this.studentListMapper = studentListMapper;
    }

    @DeleteMapping("/delete")
    public void deleteStudentGroup(@RequestParam("name") String name) {
        studentGroupService.deleteByName(name);
    }
}

package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.TeachersJournalListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.TeachersJournalMapper;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.interfaces.TeachersJournalService;

import java.util.List;

@RestController
@RequestMapping("/journals")
public class TeachersJournalsController {
    private final TeachersJournalService journalService;
    private final TeachersJournalMapper journalMapper;
    private final TeachersJournalListMapper journalListMapper;

    @Autowired
    public TeachersJournalsController(TeachersJournalService journalService,
                                      TeachersJournalMapper journalMapper,
                                      TeachersJournalListMapper journalListMapper) {
        this.journalService = journalService;
        this.journalMapper = journalMapper;
        this.journalListMapper = journalListMapper;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public TeachersJournalGetDTO findJournalById(@PathVariable("id") Long id) {
        return journalMapper.toJournalGetDTO(journalService.findOne(id));
    }

    @GetMapping("teacher/{uid}")
    @ResponseStatus(HttpStatus.FOUND)
    public TeachersJournalGetDTO findJournalByTeacher(@PathVariable("uid") Long id) {
        return journalMapper.toJournalGetDTO(journalService.findOneByTeacher(id));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.FOUND)
    public List<TeachersJournalSlimGetDTO> findAllJournals(@RequestParam(value = "page", required = false) Integer page,
                                                           @RequestParam(value = "journalsPerPage", required = false) Integer journalsPerPage,
                                                           @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                           @RequestParam(value = "faculty", required = false) Long facultyId) {
        return journalListMapper.toTeachersJournalsSlimGetDTO(journalService.findAll(new FindAllData(page, journalsPerPage, deleted, facultyId)));
    }

    @DeleteMapping("{id}/delete")
    public void deleteJournal(@PathVariable("id") Long id) {
        journalService.delete(id);
    }
    @DeleteMapping("{id}/softDelete")
    public void softDeleteJournal(@PathVariable("id") Long id) {
        journalService.softDelete(id);
    }
}

package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.TeachersJournalListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.TeachersJournalMapper;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.teachersJournal.UpdateTeachersJournalData;
import ua.dgma.electronicDeansOffice.services.interfaces.TeachersJournalService;

import javax.validation.Valid;
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

    @GetMapping("/findById")
    @ResponseStatus(HttpStatus.FOUND)
    public TeachersJournalGetDTO findJournalById(@RequestParam("id") Long id) {
        return journalMapper.toJournalGetDTO(journalService.findByid(id));
    }

    @GetMapping("/findByTeacher")
    @ResponseStatus(HttpStatus.FOUND)
    public TeachersJournalGetDTO findJournalByTeacher(@RequestParam("uid") Long uid) {
        return journalMapper.toJournalGetDTO(journalService.findByTeacherUid(uid));
    }

    @GetMapping("/findByComment")
    @ResponseStatus(HttpStatus.FOUND)
    public List<TeachersJournalSlimGetDTO> findJournalByComment(@RequestParam("comment") String comment) {
        return journalListMapper.toTeachersJournalsSlimGetDTO(journalService.findByComment(comment));
    }

    @GetMapping()
    public List<TeachersJournalSlimGetDTO> findAllJournals(@RequestParam(value = "page", required = false) Integer page,
                                                           @RequestParam(value = "journalsPerPage", required = false) Integer journalsPerPage,
                                                           @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                           @RequestParam(value = "faculty", required = false) String facultyName) {
        return journalListMapper.toTeachersJournalsSlimGetDTO(journalService.findAll(new FindAllData(page, journalsPerPage, deleted, facultyName)));
    }

    @PatchMapping("/update")
    public void updateJournal(@RequestParam("id") Long id,
                              @RequestBody @Valid TeachersJournalPatchDTO journalPatchDTO,
                                                  BindingResult bindingResult) {

        TeachersJournal updatedJournal = journalMapper.toJournal(journalPatchDTO);

        journalService.updateById(new UpdateTeachersJournalData(id, updatedJournal, bindingResult));
    }

    @DeleteMapping("/delete")
    public void deleteJournal(@RequestParam("id") Long id) {
        journalService.deleteById(id);
    }
    @DeleteMapping("/soft/delete")
    public void softDeleteJournal(@RequestParam("id") Long id) {
        journalService.softDeleteById(id);
    }
}

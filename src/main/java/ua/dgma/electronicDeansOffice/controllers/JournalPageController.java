package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPageGetDto;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPagePatchDto;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPagePostDto;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.JournalPageListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.JournalPageMapper;
import ua.dgma.electronicDeansOffice.models.JournalPage;
import ua.dgma.electronicDeansOffice.services.impl.data.journalPage.RegisterJournalPageData;
import ua.dgma.electronicDeansOffice.services.impl.data.journalPage.UpdateJournalPageData;
import ua.dgma.electronicDeansOffice.services.interfaces.JournalPageService;

import javax.validation.Valid;

@RestController
@RequestMapping("/pages")
public class JournalPageController {
    private final JournalPageService pageService;
    private final JournalPageMapper pageMapper;
    private final JournalPageListMapper pageListMapper;

    @Autowired
    public JournalPageController(JournalPageService pageService,
                                 JournalPageMapper pageMapper,
                                 JournalPageListMapper pageListMapper) {
        this.pageService = pageService;
        this.pageMapper = pageMapper;
        this.pageListMapper = pageListMapper;
    }

    @GetMapping("/findById")
    @ResponseStatus(HttpStatus.FOUND)
    public JournalPageGetDto findJournalPageById(@RequestParam("id") Long id) {
        return pageMapper.toPageGetDTO(pageService.findOne(id));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewJournalPage(@RequestBody @Valid JournalPagePostDto journalPagePostDto,
                                                           BindingResult bindingResult) {
        JournalPage newJournalPage = pageMapper.toJournalPage(journalPagePostDto);

        pageService.register(new RegisterJournalPageData(newJournalPage, bindingResult));
    }
    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateJournalPage(@RequestParam("id") Long id,
                            @RequestBody @Valid JournalPagePatchDto journalPagePatchDto,
                                                BindingResult bindingResult) {
        JournalPage updatedJournalPage = pageMapper.toJournalPage(journalPagePatchDto);

        pageService.update(new UpdateJournalPageData(id, updatedJournalPage, bindingResult));
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam("id") Long id) {
        pageService.delete(id);
    }
}

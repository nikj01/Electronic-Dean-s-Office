package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPageGetDto;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPagePatchDto;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPagePostDto;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPageSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.EventListMapper;
import ua.dgma.electronicDeansOffice.models.JournalPage;

@Mapper(componentModel = "spring", uses = EventListMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface JournalPageMapper {
    @Named("postPage")
    JournalPage toJournalPage(JournalPagePostDto journalPage);
    @Named("patchPage")
    JournalPage toJournalPage(JournalPagePatchDto journalPage);
    @Named("page")
    JournalPageGetDto toPageGetDTO(JournalPage page);
    @Named("slimPage")
    JournalPageSlimGetDTO toPageSlimGetDTO(JournalPage page);
}

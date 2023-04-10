package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPageGetDto;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPagePatchDto;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPagePostDto;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPageSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.StudentGroupListMapper;
import ua.dgma.electronicDeansOffice.models.JournalPage;

@Mapper(componentModel = "spring", uses = {StudentGroupMapper.class, StudentGroupListMapper.class, TeachersJournalMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface JournalPageMapper {
    @Named(value = "postPage")
    JournalPage toJournalPage(JournalPagePostDto journalPage);
    @Named(value = "patchPage")
    JournalPage toJournalPage(JournalPagePatchDto journalPage);
    @Named(value = "page")
    JournalPageGetDto toPageGetDTO(JournalPage page);
    @Named(value = "slimPage")
    JournalPageSlimGetDTO toPageSlimGetDTO(JournalPage page);
}

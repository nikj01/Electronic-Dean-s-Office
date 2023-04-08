package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPageSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.StudentGroupListMapper;
import ua.dgma.electronicDeansOffice.models.JournalPage;

@Mapper(componentModel = "spring", uses = {StudentGroupListMapper.class, TeachersJournalMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface JournalPageMapper {

    @Named(value = "slimPage")
    JournalPageSlimGetDTO toPageSlimGetDTO(JournalPage page);
}

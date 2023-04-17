package ua.dgma.electronicDeansOffice.mapstruct.mappers.collections;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.journalPage.JournalPageSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.JournalPageMapper;
import ua.dgma.electronicDeansOffice.models.JournalPage;

import java.util.List;

@Mapper(componentModel = "spring", uses = JournalPageMapper.class , injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface JournalPageListMapper {
    @IterableMapping(qualifiedByName = "slimPage")
    List<JournalPageSlimGetDTO> toJournalPagesSlimGetDTO(List<JournalPage> journalPages);
}

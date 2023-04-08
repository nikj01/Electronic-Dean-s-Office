package ua.dgma.electronicDeansOffice.mapstruct.mappers.collections;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.TeachersJournalMapper;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;

import java.util.List;

@Mapper(componentModel = "spring", uses = TeachersJournalMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TeachersJournalListMapper {
    @IterableMapping(qualifiedByName = "journal")
    List<TeachersJournalGetDTO> toTeachersJournalsGetDTO(List<TeachersJournal> teachers);
    @IterableMapping(qualifiedByName = "slimJournal")
    List<TeachersJournalSlimGetDTO> toTeachersJournalsSlimGetDTO(List<TeachersJournal> teachers);
}

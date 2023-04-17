package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalSlimGetDTO;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TeachersJournalMapper {
    @Named("journal")
    TeachersJournalGetDTO toJournalGetDTO(TeachersJournal teachersJournal);
    @Named("slimJournal")
    TeachersJournalSlimGetDTO toJournalSlimGetDTO(TeachersJournal teachersJournal);
}

package ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.teachersJournal.TeachersJournalSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.JournalPageListMapper;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class, JournalPageListMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TeachersJournalMapper {
    TeachersJournal toJournal(TeachersJournalPatchDTO journalPatch);
    @Named(value = "journal")
    TeachersJournalGetDTO toJournalGetDTO(TeachersJournal teachersJournal);
    @Named(value = "slimJournal")
    TeachersJournalSlimGetDTO toJournalSlimGetDTO(TeachersJournal teachersJournal);
}

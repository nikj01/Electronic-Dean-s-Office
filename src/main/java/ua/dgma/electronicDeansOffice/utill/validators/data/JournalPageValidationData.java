package ua.dgma.electronicDeansOffice.utill.validators.data;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import ua.dgma.electronicDeansOffice.models.JournalPage;
import ua.dgma.electronicDeansOffice.repositories.JournalPageRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.TeachersJournalRepository;

@Getter
@Setter
@RequiredArgsConstructor
public class JournalPageValidationData {
    @NonNull
    private JournalPage journalPage;
    @NonNull
    private JournalPageRepository pageRepository;
    @NonNull
    private TeachersJournalRepository journalRepository;
    @NonNull
    private StudentGroupRepository groupRepository;
    @NonNull
    private Errors errors;
}

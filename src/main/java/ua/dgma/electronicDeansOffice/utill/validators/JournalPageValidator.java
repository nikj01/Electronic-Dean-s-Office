package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.JournalPage;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.repositories.JournalPageRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.TeachersJournalRepository;
import ua.dgma.electronicDeansOffice.utill.validators.data.JournalPageValidationData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JournalPageValidator implements Validator {
    private final JournalPageRepository pageRepository;
    private final TeachersJournalRepository journalRepository;
    private final StudentGroupRepository groupRepository;

    @Autowired
    public JournalPageValidator(JournalPageRepository pageRepository,
                                TeachersJournalRepository journalRepository,
                                StudentGroupRepository groupRepository) {
        this.pageRepository = pageRepository;
        this.journalRepository = journalRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return JournalPage.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JournalPage page = (JournalPage) target;
        JournalPageValidationData validationData = new JournalPageValidationData(page, pageRepository, journalRepository, groupRepository, errors);

        if (checkExistenceOfTheJournalPage(validationData)) {
            checkExistenceOfTheStudentGroups(validationData);
        } else {
            checkExistenceOfTheJournal(validationData);
            checkExistenceOfTheStudentGroups(validationData);
        }
    }

    private boolean checkExistenceOfTheJournalPage(JournalPageValidationData data) {
        if (data.getJournalPage().getId() == null) return true;
        else return false;
    }

    private void checkExistenceOfTheJournal(JournalPageValidationData data) {
        if (!findJournalById(data).isPresent())
            data.getErrors().rejectValue("journal", "Journal with id " + getJournalsIdFromPage(data) + " does not exist!");
    }

    private Optional<TeachersJournal> findJournalById(JournalPageValidationData data) {
        return data.getJournalRepository().findById(getJournalsIdFromPage(data));
    }

    private Long getJournalsIdFromPage(JournalPageValidationData data) {
        return data.getJournalPage().getJournal().getId();
    }

    private void checkExistenceOfTheStudentGroups(JournalPageValidationData data) {
        if (getStudentGroupsNamesFromPage(data) != null)
            for (String studentGroup : getStudentGroupsNamesFromPage(data))
                findStudentGroupByName(data, studentGroup);
    }

    private List<String> getStudentGroupsNamesFromPage(JournalPageValidationData data) {
        return data.getJournalPage().getStudentGroups().stream().map(studentGroup -> studentGroup.getName()).collect(Collectors.toList());
    }

    private void findStudentGroupByName(JournalPageValidationData data, String name) {
        if (!data.getGroupRepository().getByName(name).isPresent())
            data.getErrors().rejectValue("studentGroup", "Student group with name " + name + " does not exist!");
    }

}

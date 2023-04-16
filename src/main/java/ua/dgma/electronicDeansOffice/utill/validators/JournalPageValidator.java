package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.JournalPage;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.repositories.TeachersJournalRepository;

import java.util.List;
import java.util.Optional;

@Component
public class JournalPageValidator implements Validator {
    private final TeachersJournalRepository journalRepository;
    private final StudentGroupRepository groupRepository;

    @Autowired
    public JournalPageValidator(TeachersJournalRepository journalRepository,
                                StudentGroupRepository groupRepository) {
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

        if (checkExistenceOfTheJournalPage(page)) {
            checkExistenceOfTheStudentGroups(page, errors);
        } else {
            checkExistenceOfTheJournal(page, errors);
            checkExistenceOfTheStudentGroups(page, errors);
        }
    }

    private boolean checkExistenceOfTheJournalPage(JournalPage page) {
        if (page.getId() == null) return true;
        else return false;
    }

    private void checkExistenceOfTheJournal(JournalPage page, Errors errors) {
        if (!findJournalById(page).isPresent())
            errors.rejectValue("journal", "Journal with id " + getJournalsIdFromPage(page) + " does not exist!");
    }

    private Optional<TeachersJournal> findJournalById(JournalPage page) {
        return journalRepository.findById(getJournalsIdFromPage(page));
    }

    private Long getJournalsIdFromPage(JournalPage page) {
        return getJournalFromPage(page).getId();
    }

    private TeachersJournal getJournalFromPage(JournalPage page) {
        return page.getJournal();
    }

    private void checkExistenceOfTheStudentGroups(JournalPage page, Errors errors) {
        if (getGroupsFromPage(page) != null)
            for (StudentGroup studentGroup : getGroupsFromPage(page))
                findStudentGroup(studentGroup, errors);
    }

    private List<StudentGroup> getGroupsFromPage(JournalPage page) {
        return page.getStudentGroups();
    }

    private void findStudentGroup(StudentGroup studentGroup, Errors errors) {
        if (!findStudentGroup(studentGroup).isPresent())
            errors.rejectValue("studentGroup", "Student group with name " + getGroupName(studentGroup) + " does not exist!");
    }

    private Optional<StudentGroup> findStudentGroup(StudentGroup studentGroup) {
        return groupRepository.findById(getGroupId(studentGroup));
    }

    private Long getGroupId(StudentGroup studentGroup) {
        return studentGroup.getId();
    }

    private String getGroupName(StudentGroup studentGroup) {
        return studentGroup.getName();
    }

}

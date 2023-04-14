package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dgma.electronicDeansOffice.models.Event;
import ua.dgma.electronicDeansOffice.models.JournalPage;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.EventRepository;
import ua.dgma.electronicDeansOffice.repositories.JournalPageRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;

import java.util.List;

@Component
public class EventValidator implements Validator {
    private final EventRepository eventRepository;
    private final StudentGroupRepository groupRepository;
    private final JournalPageRepository pageRepository;

    @Autowired
    public EventValidator(EventRepository eventRepository,
                          StudentGroupRepository groupRepository,
                          JournalPageRepository pageRepository) {
        this.eventRepository = eventRepository;
        this.groupRepository = groupRepository;
        this.pageRepository = pageRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Event.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Event event = (Event) target;

        if (checkExistenceOfTheEvent(event)) {
            checkExistenceOfTheStudentGroups(event, errors);
        } else {
            checkExistenceOfTheStudentGroups(event, errors);
            checkExistenceOfTheJournalPage(event, errors);
        }
    }

    private boolean checkExistenceOfTheEvent(Event event) {
        if (event.getId() == null) return true;
        else return false;
    }

    private void checkExistenceOfTheStudentGroups(Event event, Errors errors) {
        if(getGroupsFromEvent(event) != null)
            for (StudentGroup group : getGroupsFromEvent(event))
                checkExistenceOfTheGroup(group, errors);
    }

    private void checkExistenceOfTheGroup(StudentGroup studentGroup, Errors errors) {
        if(!groupRepository.findById(getGroupId(studentGroup)).isPresent())
            errors.rejectValue("student_group", "Student group with name " + studentGroup.getName() + " and Id " +studentGroup.getId() + " does not exist!");
    }

    private Long getGroupId(StudentGroup studentGroup) {
        return studentGroup.getId();
    }

    private List<StudentGroup> getGroupsFromEvent(Event event) {
        return event.getStudentGroups();
    }

    private void checkExistenceOfTheJournalPage(Event event, Errors errors) {
        if(!pageRepository.findById(getPageId(event)).isPresent())
            errors.rejectValue("journal_page", "Journal page with name " + getPageName(event) + " and Id " + getPageId(event) + "does not exist!" );
    }

    private Long getPageId(Event event) {
        return getPageFromEvent(event).getId();
    }

    private String getPageName(Event event) {
        return getPageFromEvent(event).getPageName();
    }

    private JournalPage getPageFromEvent(Event event) {
        return event.getPage();
    }
}

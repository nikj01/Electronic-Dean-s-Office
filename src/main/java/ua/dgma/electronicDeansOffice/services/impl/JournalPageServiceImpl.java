package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.JournalPage;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.repositories.JournalPageRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.journalPage.RegisterJournalPageData;
import ua.dgma.electronicDeansOffice.services.impl.data.journalPage.UpdateJournalPageData;
import ua.dgma.electronicDeansOffice.services.interfaces.EventService;
import ua.dgma.electronicDeansOffice.services.interfaces.JournalPageService;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;
import ua.dgma.electronicDeansOffice.services.interfaces.TeachersJournalService;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.JournalPageValidator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistenceObjectWithSuchID;

@Service
@Transactional(readOnly = true)
public class JournalPageServiceImpl implements JournalPageService {
    private final JournalPageRepository pageRepository;
    private final TeachersJournalService journalService;
    private final StudentGroupService groupService;
    private final EventService eventService;
    private final JournalPageValidator pageValidator;
    private String className;

    @Autowired
    public JournalPageServiceImpl(JournalPageRepository pageRepository,
                                  TeachersJournalService journalService,
                                  StudentGroupService groupService,
                                  EventService eventService,
                                  JournalPageValidator pageValidator) {
        this.pageRepository = pageRepository;
        this.journalService = journalService;
        this.groupService = groupService;
        this.eventService = eventService;
        this.pageValidator = pageValidator;
        this.className = JournalPage.class.getSimpleName();
    }

    @Override
    public JournalPage findOne(Long pageId) {
        return pageRepository.findById(pageId).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "id", pageId)));
    }

    @Override
    @Transactional
    public void register(RegisterJournalPageData data) {
        validateObject(new ValidationData<>(pageValidator, getNewPage(data), getBindingResult(data)));

        JournalPage newJournalPage = getNewPage(data);

        setTeachersJournalInNewPage(newJournalPage);
        setStudentGroupsInNewPage(newJournalPage);

        saveJournalPage(newJournalPage);
    }

    private JournalPage getNewPage(RegisterJournalPageData data) {
        return data.getNewJournalPage();
    }

    private BindingResult getBindingResult(RegisterJournalPageData data) {
        return data.getBindingResult();
    }

    private void setTeachersJournalInNewPage(JournalPage page) {
        page.setJournal(getJournal(page));
    }

    private TeachersJournal getJournal(JournalPage page) {
        return journalService.findOne(getJournalId(page));
    }

    private Long getJournalId(JournalPage page) {
        return page.getJournal().getId();
    }

    private void setStudentGroupsInNewPage(JournalPage page) {
        if (page.getStudentGroups() != null)
            page.setStudentGroups(getStudentGroupsFromPage(page));
    }

    private Set<StudentGroup> getStudentGroupsFromPage(JournalPage journalPage) {
        Set<StudentGroup> existingStudentGroups = new HashSet<>();

        for (StudentGroup group : getStudentGroups(journalPage))
            existingStudentGroups.add(getStudentGroup(group.getId()));

        return existingStudentGroups;
    }

    private Set<StudentGroup> getStudentGroups(JournalPage page) {
        return page.getStudentGroups();
    }

    private StudentGroup getStudentGroup(Long groupId) {
        return groupService.findOne(groupId);
    }

    private void saveJournalPage(JournalPage page) {
        pageRepository.save(page);
    }

    @Override
    @Transactional
    public void update(UpdateJournalPageData data) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, getPageId(data), pageRepository));
        validateObject(new ValidationData<>(pageValidator, getUpdatedPage(data), getBindingResult(data)));

        JournalPage existingJournalPage = findOne(getPageId(data));
        JournalPage updatedJournalPage = getUpdatedPage(data);

        setNewNameInExistingPage(existingJournalPage, updatedJournalPage);
        setUpdatedStudentGroupsInExistingPage(existingJournalPage, updatedJournalPage);
        setArchiveFlagInExistingPage(existingJournalPage, updatedJournalPage);

        saveJournalPage(existingJournalPage);
    }

    private Long getPageId(UpdateJournalPageData data) {
        return data.getId();
    }

    private JournalPage getUpdatedPage(UpdateJournalPageData data) {
        JournalPage updatedPage = getPage(data);

        setIdInPage(updatedPage, data);

        return updatedPage;
    }

    private JournalPage getPage(UpdateJournalPageData data) {
        return data.getUpdatedJournalPage();
    }

    private void setIdInPage(JournalPage page, UpdateJournalPageData data) {
        page.setId(getPageId(data));
    }

    private BindingResult getBindingResult(UpdateJournalPageData data) {
        return data.getBindingResult();
    }

    private void setNewNameInExistingPage(JournalPage existingPage, JournalPage updatedPage) {
        existingPage.setPageName(updatedPage.getPageName());
    }

    private void setUpdatedStudentGroupsInExistingPage(JournalPage existingPage, JournalPage updatedPage) {
        if (getStudentGroupsFromPage(updatedPage) != null) {
            existingPage.setStudentGroups(getStudentGroupsFromPage(updatedPage));
            setUpdatedStudentGroupsInEvents(existingPage, updatedPage);
        }
        else
            removeStudentGroupsFromPage(existingPage);
    }

    private void setUpdatedStudentGroupsInEvents(JournalPage existingPage, JournalPage updatedPage) {
        existingPage.getEvents().stream().forEach(event -> event.setStudentGroups(getStudentGroupsFromPage(updatedPage).stream().toList()));
    }

    private void removeStudentGroupsFromPage(JournalPage existingPage) {
        removeStudentGroupsFromEvents(existingPage);

        existingPage.setStudentGroups(new HashSet<>());
    }

    private void removeStudentGroupsFromEvents(JournalPage page) {
        eventService.removeStudentGroupsFromEvents(getPageId(page));
    }

    private Long getPageId(JournalPage page) {
        return page.getId();
    }

    private void setArchiveFlagInExistingPage(JournalPage existingPage, JournalPage updatedPage) {
        existingPage.setArchive(updatedPage.isArchive());
        existingPage.setWasArchived(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void delete(Long pageId) {
        checkExistenceObjectWithSuchID(new CheckExistsByIdData<>(className, pageId, pageRepository));

        pageRepository.deleteById(pageId);
    }
}

package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.JournalPage;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.repositories.JournalPageRepository;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.journalPage.RegisterJournalPageData;
import ua.dgma.electronicDeansOffice.services.impl.data.journalPage.UpdateJournalPageData;
import ua.dgma.electronicDeansOffice.services.interfaces.JournalPageService;
import ua.dgma.electronicDeansOffice.services.interfaces.TeachersJournalService;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.JournalPageValidator;

import java.util.ArrayList;
import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;

@Service
@Transactional(readOnly = true)
public class JournalPageServiceImpl implements JournalPageService {
    private final JournalPageRepository pageRepository;
    private final TeachersJournalService journalService;
    private final StudentGroupRepository groupRepository;
    private final JournalPageValidator pageValidator;
    private String className;

    @Autowired
    public JournalPageServiceImpl(JournalPageRepository pageRepository,
                                  TeachersJournalService journalService,
                                  StudentGroupRepository groupRepository,
                                  JournalPageValidator pageValidator) {
        this.pageRepository = pageRepository;
        this.journalService = journalService;
        this.groupRepository = groupRepository;
        this.pageValidator = pageValidator;
        this.className = JournalPage.class.getSimpleName();
    }

    @Override
    public JournalPage findById(Long id) {
        return pageRepository.findById(id).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "id", id)));
    }

    @Override
    @Transactional
    public void registerNew(RegisterJournalPageData data) {
        validateObject(new ValidationData<>(pageValidator, data.getNewJournalPage(), data.getBindingResult()));

        JournalPage newJournalPage = data.getNewJournalPage();

        setTeachersJournalInNewPage(newJournalPage);
        setStudentGroupsInNewPage(newJournalPage);

        saveJournalPage(newJournalPage);
    }

    public void setTeachersJournalInNewPage(JournalPage page) {
        page.setJournal(getJournal(page));
    }

    private TeachersJournal getJournal(JournalPage page) {
        return journalService.findByid(getJournalId(page));
    }

    private Long getJournalId(JournalPage page) {
        return page.getJournal().getId();
    }

    public void setStudentGroupsInNewPage(JournalPage page) {
//        if (page.getStudentGroups() != null)
            page.setStudentGroups(getStudentGroupsFromPage(page));
    }

    public List<StudentGroup> getStudentGroupsFromPage(JournalPage journalPage) {
        List<StudentGroup> existingStudentGroups = new ArrayList<>();

        for (StudentGroup group : journalPage.getStudentGroups())
            existingStudentGroups.add(getStudentGroup(group.getName()));

        return existingStudentGroups;
    }

    private StudentGroup getStudentGroup(String groupName) {
        return groupRepository.getByName(groupName).get();
    }

    public void saveJournalPage(JournalPage page) {
        pageRepository.save(page);
    }

    @Override
    @Transactional
    public void update(UpdateJournalPageData data) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, data.getId(), pageRepository));
        validateObject(new ValidationData<>(pageValidator, data.getUpdatedJournalPage(), data.getBindingResult()));

        JournalPage existingJournalPage = findById(data.getId());
        JournalPage updatedJournalPage = data.getUpdatedJournalPage();

        setNewNameOnExistingPage(existingJournalPage, updatedJournalPage);
        setUpdatedStudentGroupsOnExistingPage(existingJournalPage, updatedJournalPage);
        setArchiveFlagOnExistingPage(existingJournalPage, updatedJournalPage);

        saveJournalPage(existingJournalPage);
    }

    public void setNewNameOnExistingPage(JournalPage existingPage, JournalPage updatedPage) {
        existingPage.setPageName(updatedPage.getPageName());
    }

    public void setUpdatedStudentGroupsOnExistingPage(JournalPage existingPage, JournalPage updatedPage) {
        existingPage.setStudentGroups(getStudentGroupsFromPage(updatedPage));
    }

    public void setArchiveFlagOnExistingPage(JournalPage existingPage, JournalPage updatedPage) {
        existingPage.setArchive(updatedPage.isArchive());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, id, pageRepository));

        pageRepository.deleteById(id);
    }
}

package ua.dgma.electronicDeansOffice.services.specifications.impl;

import org.springframework.data.jpa.domain.Specification;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.services.specifications.DeletedSpecification;

public interface TeachersJournalSpecifications extends DeletedSpecification {
    Specification<TeachersJournal> getTeacherJournalByFacultyCriteria(String facultyName);
}

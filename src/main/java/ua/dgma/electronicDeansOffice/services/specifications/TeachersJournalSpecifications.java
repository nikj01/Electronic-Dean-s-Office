package ua.dgma.electronicDeansOffice.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;

public interface TeachersJournalSpecifications extends DeletedSpecification {
    Specification<TeachersJournal> getTeacherJournalByFacultyCriteria(Long facultyId);
}

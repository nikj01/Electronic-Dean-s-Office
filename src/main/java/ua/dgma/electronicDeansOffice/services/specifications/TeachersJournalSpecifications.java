package ua.dgma.electronicDeansOffice.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import ua.dgma.electronicDeansOffice.models.TeachersJournal;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;

public interface TeachersJournalSpecifications extends DeletedSpecification {
    Specification<TeachersJournal> getSpecForTeacherJournals(FindAllData data);
}

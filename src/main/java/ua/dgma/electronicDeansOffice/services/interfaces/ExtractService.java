package ua.dgma.electronicDeansOffice.services.interfaces;

import ua.dgma.electronicDeansOffice.mapstruct.dtos.extractWithGrades.Extract;
import ua.dgma.electronicDeansOffice.models.Report;

public interface ExtractService {
    Extract getExtractWithGradeForStudent(Report report, Long studentId);
}

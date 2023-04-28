package ua.dgma.electronicDeansOffice.services.impl.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FindAllData {
    private Integer page;
    private Integer objectsPerPage;
    private Boolean deleted;
    private Long facultyId;
    private LocalDateTime searchFrom;
    private LocalDateTime searchTo;

    public FindAllData(Integer page,
                       Integer objectsPerPage,
                       Boolean deleted,
                       Long facultyId) {
        this.page = page;
        this.objectsPerPage = objectsPerPage;
        this.deleted = deleted;
        this.facultyId = facultyId;
    }

    public FindAllData(Integer page,
                       Integer objectsPerPage,
                       Boolean deleted) {
        this.page = page;
        this.objectsPerPage = objectsPerPage;
        this.deleted = deleted;
    }

    public FindAllData(Long facultyId,
                       LocalDateTime searchFrom,
                       LocalDateTime searchTo,
                       Boolean deleted) {
        this.facultyId = facultyId;
        this.searchFrom = searchFrom;
        this.searchTo = searchTo;
        this.deleted = deleted;
    }
}
